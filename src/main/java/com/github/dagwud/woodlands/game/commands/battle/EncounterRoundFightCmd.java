package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.character.ExpireSpellsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.PassiveBattleRoundSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;
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
    encounter.setFightingStarted(true);

    List<DamageInflicted> roundActivity = new ArrayList<>();

    if (shouldEnemyFaint())
    {
      KnockUnconsciousCmd faint = new KnockUnconsciousCmd(encounter.getEnemy());
      CommandDelegate.execute(faint);
    }

    // Note that we need to keep re-checking order of fight since the fighters involved may change (e.g. due
    // to spells like Army of Peasants)
    List<Fighter> passivesOrder = buildOrderOfFight(encounter.getAllFighters());
    List<PassiveBattleRoundSpell> passivesActivity = doPassiveAbilities(passivesOrder);

    List<Fighter> spellOrder = buildOrderOfFight(encounter.getAllFighters());
    List<SingleCastSpell> spellsActivity = doPreparedSpells(spellOrder);
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

    BuildRoundSummaryCmd summary = new BuildRoundSummaryCmd(encounter, roundActivity, passivesActivity, spellsActivity);
    CommandDelegate.execute(summary);
    SendPartyMessageCmd status = new SendPartyMessageCmd(encounter.getParty(), summary.getSummary());
    CommandDelegate.execute(status);

    if (!encounter.getEnemy().isConscious() || !anyPlayerCharactersStillAlive(encounter))
    {
      if (!encounter.getEnemy().isConscious())
      {
        DefeatCreatureCmd win = new DefeatCreatureCmd(encounter.getParty(), encounter.getEnemy(), encounter.isFarmed());
        CommandDelegate.execute(win);
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

  private boolean shouldEnemyFaint()
  {
    Collection<PlayerCharacter> players = encounter.getParty().getActivePlayerCharacters();
    for (PlayerCharacter c : players)
    {
      if (c.shouldGainExperienceByDefeating(encounter.getEnemy()))
      {
        return false;
    }
    return true;
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

  private boolean anyPlayerCharactersStillAlive(Encounter encounter)
  {
    for (PlayerCharacter member : encounter.getParty().getActivePlayerCharacters())
    {
      if (member.isConscious())
      {
        return true;
      }
    }
    return false;
  }

  private List<DamageInflicted> doAttack(Fighter attacker, List<SingleCastSpell> spellsActivity)
  {
    List<DamageInflicted> roundActivity = new ArrayList<>();

    int spellsCast = countSpellsCastBy(spellsActivity, attacker);
    int attacksAllowed = encounter.getActionsAllowedPerRound() - spellsCast;
    int totalAttacksAllowed = attacksAllowed;

    Fighter defender = attacker.chooseFighterToAttack(encounter.getAllFighters());
    if (attacker.isConscious() && defender.isConscious() && attacksAllowed > 0)
    {
      DamageInflicted damage = doAttack(attacker, attacker.getCarrying().getCarriedLeft(), defender);
      if (damage.getHitStatus() != EHitStatus.DO_NOTHING)
      {
        roundActivity.add(damage);
      }
      attacksAllowed--;
    }
    if (attacker.isConscious() && defender.isConscious() && attacksAllowed > 0)
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
