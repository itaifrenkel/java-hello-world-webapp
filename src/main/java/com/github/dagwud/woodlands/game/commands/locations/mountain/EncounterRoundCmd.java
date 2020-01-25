package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.battle.DealDamageCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EncounterRoundCmd extends AbstractCmd
{
  private final int chatId;
  private final Encounter encounter;
  private final int delayBetweenRoundsMS;

  EncounterRoundCmd(int chatId, Encounter encounter, int delayBetweenRoundsMS)
  {
    super(new EncounterNotEndedPrerequisite(encounter));
    this.chatId = chatId;
    this.encounter = encounter;
    this.delayBetweenRoundsMS = delayBetweenRoundsMS;
  }

  @Override
  public void execute()
  {
    List<Spell> spellsActivity = doPassiveAbilities();
    List<DamageInflicted> roundActivity = doFighting();
    expirePassiveAbilities(spellsActivity);

    String summary = buildRoundSummary(spellsActivity, roundActivity);
    SendPartyMessageCmd status = new SendPartyMessageCmd(encounter.getParty(), summary);
    CommandDelegate.execute(status);

    GameCharacter inDanger = getAnyPlayerInDanger();
    if (inDanger != null)
    {
      if (encounter.getParty().capableOfRetreat())
      {
        RetreatCmd retreat = new RetreatCmd(inDanger);
        CommandDelegate.execute(retreat);
      }
      else
      {
        TooManyUnconsciousCmd killall = new TooManyUnconsciousCmd(encounter.getParty());
        CommandDelegate.execute(killall);
      }
      
      EndEncounterCmd end = new EndEncounterCmd(encounter);
      CommandDelegate.execute(end);
      
      return;
    }

    if (encounter.getEnemy().getStats().getState() != EState.ALIVE || !anyPlayerCharactersStillAlive(encounter))
    {
      EndEncounterCmd end = new EndEncounterCmd(encounter);
      CommandDelegate.execute(end);
    }

    if (!encounter.isEnded())
    {
      scheduleNextRound();
    }
    else
    {
      if (!encounter.getParty().canAct())
      {
        SendPartyMessageCmd cmd = new SendPartyMessageCmd(encounter.getParty(), "You have been defeated!");
        CommandDelegate.execute(cmd);
      }
      else if (encounter.getEnemy().getStats().getState() != EState.ALIVE)
      {
        DefeatCreatureCmd win = new DefeatCreatureCmd(encounter.getParty(), encounter.getEnemy());
        CommandDelegate.execute(win);

        SendPartyMessageCmd cmd = new SendPartyMessageCmd(encounter.getParty(), encounter.getEnemy().name + " has been defeated! Each player gains " + win.getExperienceGrantedPerPlayer() + " experience");
        CommandDelegate.execute(cmd);
      }
      else
      {
        SendPartyMessageCmd cmd = new SendPartyMessageCmd(encounter.getParty(), "Evan is really smart.... enemy " + encounter.getEnemy().name + " is " + encounter.getEnemy().getStats().getState());
        CommandDelegate.execute(cmd);
      }
    }
  }

  private void expirePassiveAbilities(List<Spell> spellsActivity)
  {
    for (Spell spell : spellsActivity)
    {
      spell.expire();
    }
  }

  private List<Spell> doPassiveAbilities()
  {
    List<Spell> toCast = new ArrayList<>();
    for (GameCharacter member : encounter.getParty().getMembers())
    {
      toCast.addAll(member.castPassives());
    }

    for (Spell spellToCast : toCast)
    {
      spellToCast.cast();
    }

    return toCast;
  }

  private List<DamageInflicted> doFighting()
  {
    encounter.incrementBattleRound();
    List<DamageInflicted> roundActivity = new LinkedList<>();

    for (GameCharacter partyMember : encounter.getParty().getMembers())
    {
      doAttack(partyMember, roundActivity);
    }
    doAttack(encounter.getEnemy(), roundActivity);
    return roundActivity;
  }

  private GameCharacter getAnyPlayerInDanger()
  {
    for (GameCharacter member : encounter.getParty().getMembers())
    {
      double hpPerc = ((double) member.getStats().getHitPoints()) / ((double) member.getStats().getMaxHitPoints());
      if (hpPerc <= 0.2)
      {
        return member;
      }
    }
    return null;
  }

  private boolean anyPlayerCharactersStillAlive(Encounter encounter)
  {
    for (GameCharacter member : encounter.getParty().getMembers())
    {
      if (member.getStats().getState() == EState.ALIVE)
      {
        return true;
      }
    }
    return false;
  }

  private void doAttack(Fighter attacker, List<DamageInflicted> roundActivity)
  {
    Fighter defender = determineDefender(attacker);
    if (attacker.getStats().getState() == EState.ALIVE && defender.getStats().getState() == EState.ALIVE)
    {
      DamageInflicted damage = doAttack(attacker, attacker.getCarrying().getCarriedLeft(), defender);
      roundActivity.add(damage);
    }
    if (attacker.getStats().getState() == EState.ALIVE && defender.getStats().getState() == EState.ALIVE)
    {
      DamageInflicted damage = doAttack(attacker, attacker.getCarrying().getCarriedRight(), defender);
      roundActivity.add(damage);
    }
  }

  private Fighter determineDefender(Fighter attacker)
  {
    if (attacker instanceof GameCharacter)
    {
      return encounter.getEnemy();
    }
    List<GameCharacter> members = encounter.getParty().getMembers();
    for (int i = members.size() - 1; i >= 0; i--)
    {
      GameCharacter member = members.get(i);
      if (member.getStats().getState() == EState.ALIVE)
      {
        return member;
      }
    }
    return members.get(0);
  }

  private DamageInflicted doAttack(Fighter attacker, Weapon attackWith, Fighter defender)
  {
    AttackCmd attack = new AttackCmd(attacker, attackWith, defender);
    CommandDelegate.execute(attack);
    DamageInflicted damageInflicted = attack.getDamageInflicted();

    DealDamageCmd cmd = new DealDamageCmd(damageInflicted, defender);
    CommandDelegate.execute(cmd);
    return damageInflicted;
  }

  private String buildRoundSummary(List<Spell> spells, List<DamageInflicted> damage)
  {
    StringBuilder summary = new StringBuilder();
    summary.append("⚔️ Battle Round #").append(encounter.getBattleRound()).append(": ⚔️\n")
            .append("———————————");

    for (Spell spell : spells)
    {
      summary.append("\n").append(spell.buildSpellDescription());
    }

    for (DamageInflicted damageInflicted : damage)
    {
      if (damageInflicted.getHitStatus() != EHitStatus.DO_NOTHING)
      {
        summary.append("\n").append(damageInflicted.buildDamageDescription());
      }
    }
    summary.append("\n\n").append(buildBattleStatsSummary());
    return summary.toString();
  }

  private String buildBattleStatsSummary()
  {
    StringBuilder b = new StringBuilder();
    b.append("Stats after round ").append(encounter.getBattleRound()).append("\n")
            .append("—————————");
    for (GameCharacter member : encounter.getParty().getMembers())
    {
      b.append("\n").append("• ").append(member.summary());
    }
    b.append("\n").append("• ").append(encounter.getEnemy().summary());
    return b.toString();
  }

  private void scheduleNextRound()
  {
    EncounterRoundCmd nextRoundCmd = new EncounterRoundCmd(chatId, encounter, delayBetweenRoundsMS);
    RunLaterCmd nextEncounter = new RunLaterCmd(delayBetweenRoundsMS, nextRoundCmd);
    CommandDelegate.execute(nextEncounter);
  }
}
