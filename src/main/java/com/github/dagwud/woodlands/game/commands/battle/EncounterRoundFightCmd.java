package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.character.ExpireSpellsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.PassiveBattleRoundSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EncounterRoundFightCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final Encounter encounter;
  private final PlayerState playerState;
  private final int delayBetweenRoundsMS;

  EncounterRoundFightCmd(PlayerState playerState, Encounter encounter, int delayBetweenRoundsMS)
  {
    this.playerState = playerState;
    this.encounter = encounter;
    this.delayBetweenRoundsMS = delayBetweenRoundsMS;
  }

  @Override
  public void execute()
  {
    encounter.startFighting();
    if (encounter.isEnded())
    {
      return;
    }
    encounter.setStatus(EncounterStatus.FIGHTING);

    List<DamageInflicted> roundActivity = new ArrayList<>();
    List<PassiveBattleRoundSpell> passivesActivity = new ArrayList<>();
    List<SingleCastSpell> spellsActivity = new ArrayList<>();

    int fledEnemiesCount = 0;
    for (Fighter enemy : encounter.getEnemies())
    {
      boolean enemyFled = encounter.getEnemies().size() == 1 && enemy instanceof Creature && shouldEnemyFlee((Creature)enemy);
      if (enemyFled)
      {
        KnockUnconsciousCmd faint = new KnockUnconsciousCmd(enemy);
        CommandDelegate.execute(faint);
        CommandDelegate.execute(new SendPartyMessageCmd(encounter.getParty(), enemy.getName() + " has fled!"));
        fledEnemiesCount++;
      }
    }

    if (fledEnemiesCount != encounter.getEnemies().size())
    {
      // Note that we need to keep re-checking order of fight since the fighters involved may change (e.g. due
      // to spells like Army of Peasants)
      List<Fighter> passivesOrder = buildOrderOfFight(encounter.getAllFighters());
      passivesActivity.addAll(doPassiveAbilities(passivesOrder));

      List<Fighter> spellOrder = buildOrderOfFight(encounter.getAllFighters());
      spellsActivity.addAll(doPreparedSpells(spellOrder));
      for (Spell cast : spellsActivity)
      {
        if (null != cast.getDamageInflicted())
        {
          roundActivity.add(cast.getDamageInflicted());
        }
      }

      List<Fighter> fightOrder = buildOrderOfFight(encounter.getAllFighters());
      roundActivity.addAll(doFighting(fightOrder, spellsActivity));

      expireSpells(spellsActivity);
      expirePassiveAbilities(passivesActivity);
    }

    BuildRoundSummaryCmd summary = new BuildRoundSummaryCmd(encounter, roundActivity, passivesActivity, spellsActivity);
    CommandDelegate.execute(summary);
    SendPartyMessageCmd status = new SendPartyMessageCmd(encounter.getParty(), summary.getSummary());
    CommandDelegate.execute(status);

    if (!anyEnemyConscious() || !encounter.anyAggressorsStillConscious())
    {
      for (Fighter enemy : encounter.getEnemies())
      {
        if (!enemy.isConscious())
        {
          AbstractCmd win;
          if (enemy instanceof Creature)
          {
            win = new DefeatCreatureCmd(encounter.getParty(), (Creature)enemy, encounter.isFarmed(), encounter.getEnemies().size() > 1);
          }
          else
          {
            Collection<Fighter> allFighters = encounter.getAllFighters();
            allFighters.remove(enemy);
            Fighter victor = allFighters.iterator().next();
            win = new DefeatSparringPartnerCmd(encounter.getParty(), victor, enemy);
          }
          CommandDelegate.execute(win);
        }
      }

      EndEncounterCmd end = new EndEncounterCmd(encounter);
      CommandDelegate.execute(end);
    }

    PlayerCharacter inDanger = getAnyPlayerInDanger();
    if (inDanger != null)
    {
      if (encounter.getParty().capableOfRetreat())
      {
        if (inDanger.getLocation().isAutoRetreat())
        {
          RetreatCmd retreat = new RetreatCmd(inDanger);
          CommandDelegate.execute(retreat);
          return;
        }
      }
      else
      {
        TooManyUnconsciousCmd killall = new TooManyUnconsciousCmd(encounter.getParty());
        CommandDelegate.execute(killall);

        EndEncounterCmd end = new EndEncounterCmd(encounter);
        CommandDelegate.execute(end);

        return;
      }
    }

    if (!encounter.isEnded())
    {
      scheduleNextRound();
    }
    else
    {
      if (!encounter.getParty().canAct())
      {
        SendPartyMessageCmd cmd = new SendPartyMessageCmd(encounter.getParty(), "<b>You have been defeated!</b>");
        CommandDelegate.execute(cmd);
      }
    }
  }

  private boolean anyEnemyConscious()
  {
    for (Fighter enemy : encounter.getEnemies())
    {
      if (enemy.isConscious())
      {
        return true;
      }
    }
    return false;
  }

  private boolean shouldEnemyFlee(Creature enemy)
  {
    if (Settings.SUICIDAL_CREATURES)
    {
      return false;
    }

    Collection<PlayerCharacter> players = encounter.getParty().getActivePlayerCharacters();
    for (PlayerCharacter c : players)
    {
      if (c.shouldGainExperienceByDefeating(enemy))
      {
        return false;
      }
    }
    return enemy.difficulty <= 10;
  }

  private List<Fighter> buildOrderOfFight(Collection<Fighter> fighters)
  {
    OrderFightersCmd cmd = new OrderFightersCmd(fighters);
    CommandDelegate.execute(cmd);
    return cmd.getOrderedFighters();
  }

  private void expireSpells(List<SingleCastSpell> spellsActivity)
  {
    ExpireSpellsCmd expireAll = new ExpireSpellsCmd(spellsActivity);
    CommandDelegate.execute(expireAll);
  }

  private List<SingleCastSpell> doPreparedSpells(List<Fighter> fighters)
  {
    CastPreparedSpellsCmd cast = new CastPreparedSpellsCmd(fighters, encounter);
    CommandDelegate.execute(cast);
    return cast.getSpellsCast();
  }

  private void expirePassiveAbilities(List<PassiveBattleRoundSpell> spellsActivity)
  {
    ExpireSpellsCmd expireAll = new ExpireSpellsCmd(spellsActivity);
    CommandDelegate.execute(expireAll);
  }

  private List<PassiveBattleRoundSpell> doPassiveAbilities(List<Fighter> fighters)
  {
    CastPassiveAbilitiesCmd cast = new CastPassiveAbilitiesCmd(fighters);
    CommandDelegate.execute(cast);
    return cast.getPassivesCast();
  }

  private List<DamageInflicted> doFighting(List<Fighter> fighters, List<SingleCastSpell> spellsActivity)
  {
    encounter.incrementBattleRound();
    List<DamageInflicted> roundActivity = new LinkedList<>();

    for (Fighter fighter : fighters)
    {
      roundActivity.addAll(doAttack(fighter, spellsActivity));
    }
    return roundActivity;
  }

  private PlayerCharacter getAnyPlayerInDanger()
  {
    for (PlayerCharacter member : encounter.getParty().getActivePlayerCharacters())
    {
      double hpPerc = ((double) member.getStats().getHitPoints()) / ((double) member.getStats().getMaxHitPoints().total());
      if (!member.isDead() && hpPerc <= 0.2)
      {
        return member;
      }
    }
    return null;
  }

  private List<DamageInflicted> doAttack(Fighter attacker, List<SingleCastSpell> spellsActivity)
  {
    List<DamageInflicted> roundActivity = new ArrayList<>();

    int spellsCast = countSpellsCastBy(spellsActivity, attacker);
    int attacksAllowed = encounter.getActionsAllowedPerRound() - spellsCast;
    int totalAttacksAllowed = attacksAllowed;

    Fighter defender;

    defender = encounter.chooseFighterToAttack(attacker);
    if (attacker.isConscious() && defender != null && defender.isConscious() && attacksAllowed > 0)
    {
      DamageInflicted damage = doAttack(attacker, attacker.getCarrying().getCarriedLeft(), defender);
      if (damage.getHitStatus() != EHitStatus.DO_NOTHING)
      {
        roundActivity.add(damage);
      }
      attacksAllowed--;
    }

    defender = encounter.chooseFighterToAttack(attacker);
    if (attacker.isConscious() && defender != null && defender.isConscious() && attacksAllowed > 0)
    {
      DamageInflicted damage = doAttack(attacker, attacker.getCarrying().getCarriedRight(), defender);
      if (damage.getHitStatus() != EHitStatus.DO_NOTHING)
      {
        roundActivity.add(damage);
      }
      attacksAllowed--;
    }

    if (roundActivity.isEmpty() && totalAttacksAllowed > 0)
    {
      // add a "does nothing" entry:
      roundActivity.add(doAttack(attacker, null, defender));
    }
    return roundActivity;
  }

  private int countSpellsCastBy(List<SingleCastSpell> spellsActivity, Fighter attacker)
  {
    int count = 0;
    for (SingleCastSpell spell : spellsActivity)
    {
      if (spell.getCaster() == attacker)
      {
        count++;
      }
    }
    return count;
  }

  private DamageInflicted doAttack(Fighter attacker, Item attackWith, Fighter defender)
  {
    if (!(attackWith instanceof Weapon))
    {
      return new DamageInflicted(attacker, null, EHitStatus.DO_NOTHING, 0, defender, 0);
    }

    AttackCmd attack = new AttackCmd(attacker, (Weapon)attackWith, defender);
    CommandDelegate.execute(attack);
    DamageInflicted damageInflicted = attack.getDamageInflicted();

    DealDamageCmd cmd = new DealDamageCmd(damageInflicted, defender);
    CommandDelegate.execute(cmd);
    return damageInflicted;
  }

  private void scheduleNextRound()
  {
    EncounterRoundCmd nextRoundCmd;
    nextRoundCmd = encounter.createNextRoundCmd(playerState, delayBetweenRoundsMS);
    RunLaterCmd nextEncounter = new RunLaterCmd(delayBetweenRoundsMS, nextRoundCmd);
    CommandDelegate.execute(nextEncounter);

    SendPartyMessageCmd msg = new SendPartyMessageCmd(encounter.getParty(), "Next round of battle in " + (delayBetweenRoundsMS / 1000) + " seconds");
    CommandDelegate.execute(msg);
  }
}
