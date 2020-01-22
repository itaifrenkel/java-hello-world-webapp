package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.battle.DealDamageCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Weapon;

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
    encounter.incrementBattleRound();
    List<DamageInflicted> roundActivity = new LinkedList<>();

    for (GameCharacter partyMember : encounter.getParty().getMembers())
    {
      doAttack(partyMember, roundActivity);
    }
    doAttack(encounter.getEnemy(), roundActivity);

    StringBuilder summary = buildRoundSummary(roundActivity);
    SendPartyMessageCmd status = new SendPartyMessageCmd(encounter.getParty(), summary.toString());
    CommandDelegate.execute(status);

    if (encounter.getEnemy().getStats().getState() == EState.ALIVE && anyPlayerCharactersStillAlive(encounter))
    {
      scheduleNextRound();
    }
    else
    {
      if (encounter.getEnemy().getStats().getState() == EState.ALIVE)
      {
        SendPartyMessageCmd cmd = new SendPartyMessageCmd(encounter.getParty(), "You have been defeated!");
        CommandDelegate.execute(cmd);
      }
      else
      {
        SendPartyMessageCmd cmd = new SendPartyMessageCmd(encounter.getParty(), encounter.getEnemy().name + " has been defeated!");
        CommandDelegate.execute(cmd);

        DefeatCreatureCmd win = new DefeatCreatureCmd(encounter.getParty(), encounter.getEnemy());
        CommandDelegate.execute(win);
      }
      EndEncounterCmd end = new EndEncounterCmd(encounter);
      CommandDelegate.execute(end);
    }
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

  private void doAttack(IFighter attacker, List<DamageInflicted> roundActivity)
  {
    IFighter defender = determineDefender(attacker);
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

  private IFighter determineDefender(IFighter attacker)
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

  private DamageInflicted doAttack(IFighter attacker, Weapon attackWith, IFighter defender)
  {
    AttackCmd attack = new AttackCmd(attacker, attackWith, defender);
    CommandDelegate.execute(attack);
    DamageInflicted damageInflicted = attack.getDamageInflicted();

    DealDamageCmd cmd = new DealDamageCmd(damageInflicted, defender);
    CommandDelegate.execute(cmd);
    return damageInflicted;
  }

  private StringBuilder buildRoundSummary(List<DamageInflicted> roundActivity)
  {
    StringBuilder description = new StringBuilder();
    description.append("⚔️ Battle Round #").append(encounter.getBattleRound()).append(": ⚔️\n")
            .append("———————————");
    for (DamageInflicted damageInflicted : roundActivity)
    {
      if (damageInflicted.getHitStatus() != EHitStatus.DO_NOTHING)
      {
        description.append("\n").append(damageInflicted.buildDamageDescription());
      }
    }
    description.append("\n\n").append(buildBattleStatsSummary());
    return description;
  }

  private String buildBattleStatsSummary()
  {
    StringBuilder b = new StringBuilder();
    b.append("Stats after round ").append(encounter.getBattleRound()).append("\n")
            .append("—————————");
    for (GameCharacter member : encounter.getParty().getMembers())
    {
      b.append("\n").append(buildCharacterSummaryLine(member.getName(), member.getStats()));
    }
    b.append("\n").append(buildCharacterSummaryLine(encounter.getEnemy().name, encounter.getEnemy().getStats()));
    return b.toString();
  }

  private String buildCharacterSummaryLine(String characterName, Stats stats)
  {
    if (stats.getState() == EState.DEAD)
    {
      return "• " + characterName + ": ☠️dead";
    }
    String msg = "• " + characterName
            + ": ❤️" + stats.getHitPoints() + "/" + stats.getMaxHitPoints();
    if (stats.getMaxMana() != 0)
    {
      msg += ", ✨" + stats.getMana() + "/" + stats.getMaxMana();
    }
    return msg;
  }

  private void scheduleNextRound()
  {
    EncounterRoundCmd nextRoundCmd = new EncounterRoundCmd(chatId, encounter, delayBetweenRoundsMS);
    RunLaterCmd nextEncounter = new RunLaterCmd(delayBetweenRoundsMS, nextRoundCmd);
    CommandDelegate.execute(nextEncounter);
  }
}
