package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.ExpireSpellsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.PassiveBattleRoundSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EncounterRoundCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final Encounter encounter;
  private final int delayBetweenRoundsMS;

  public EncounterRoundCmd(int chatId, Encounter encounter, int delayBetweenRoundsMS)
  {
    super(new EncounterNotEndedPrerequisite(encounter));
    this.chatId = chatId;
    this.encounter = encounter;
    this.delayBetweenRoundsMS = delayBetweenRoundsMS;
  }

  @Override
  public void execute()
  {
    if (encounter.isEnded())
    {
      return;
    }

    List<DamageInflicted> roundActivity = new ArrayList<>();

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
    roundActivity.addAll(doFighting(fightOrder));

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

        SendPartyMessageCmd cmd = new SendPartyMessageCmd(encounter.getParty(), encounter.getEnemy().name + " has been defeated! Each player gains " + win.getExperienceGrantedPerPlayer() + " experience");
        CommandDelegate.execute(cmd);
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

  private List<DamageInflicted> doFighting(List<Fighter> fighters)
  {
    encounter.incrementBattleRound();
    List<DamageInflicted> roundActivity = new LinkedList<>();

    for (Fighter fighter : fighters)
    {
      roundActivity.addAll(doAttack(fighter));
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

  private List<DamageInflicted> doAttack(Fighter attacker)
  {
    List<DamageInflicted> roundActivity = new ArrayList<>();
    Fighter defender = attacker.chooseFighterToAttack(encounter.getAllFighters());
    if (attacker.isConscious() && defender.isConscious())
    {
      DamageInflicted damage = doAttack(attacker, attacker.getCarrying().getCarriedLeft(), defender);
      if (damage.getHitStatus() != EHitStatus.DO_NOTHING)
      {
        roundActivity.add(damage);
      }
    }
    if (attacker.isConscious() && defender.isConscious())
    {
      DamageInflicted damage = doAttack(attacker, attacker.getCarrying().getCarriedRight(), defender);
      if (damage.getHitStatus() != EHitStatus.DO_NOTHING)
      {
        roundActivity.add(damage);
      }
    }

    if (roundActivity.isEmpty())
    {
      // add a "does nothing" entry:
      roundActivity.add(doAttack(attacker, null, defender));
    }
    return roundActivity;
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
    EncounterRoundCmd nextRoundCmd = new EncounterRoundCmd(chatId, encounter, delayBetweenRoundsMS);
    RunLaterCmd nextEncounter = new RunLaterCmd(delayBetweenRoundsMS, nextRoundCmd);
    CommandDelegate.execute(nextEncounter);

    SendPartyMessageCmd msg = new SendPartyMessageCmd(encounter.getParty(), "Next round of battle in " + (delayBetweenRoundsMS / 1000) + " seconds");
    CommandDelegate.execute(msg);
  }

  @Override
  public String toString()
  {
    return "EncounterRoundCmd{" +
            "chatId=" + chatId +
            ", encounter=" + encounter +
            '}';
  }
}
