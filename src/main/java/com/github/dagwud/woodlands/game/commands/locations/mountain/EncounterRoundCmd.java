package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.battle.DealDamageCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Damage;
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

    encounter.incrementBattleRound();
    List<DamageInflicted> roundActivity = new LinkedList<>();

    doAttack(encounter.getHost(), roundActivity);
    doAttack(encounter.getEnemy(), roundActivity);

    StringBuilder summary = buildRoundSummary(roundActivity);
    SendMessageCmd status = new SendMessageCmd(chatId, summary.toString());
    CommandDelegate.execute(status);

    if (encounter.getEnemy().getStats().getState() == EState.ALIVE && encounter.getHost().getStats().getState() == EState.ALIVE)
    {
      scheduleNextRound();
    }
    else
    {
      encounter.end();
      if (encounter.getEnemy().getStats().getState() == EState.ALIVE)
      {
        SendMessageCmd cmd = new SendMessageCmd(chatId, "You have been defeated!");
        CommandDelegate.execute(cmd);
      }
      else
      {
        SendMessageCmd cmd = new SendMessageCmd(chatId, encounter.getEnemy().name + " has been defeated!");
        CommandDelegate.execute(cmd);
      }
    }
  }

  private void doAttack(IFighter attacker, List<DamageInflicted> roundActivity)
  {
    IFighter defender = (attacker == encounter.getEnemy() ? encounter.getHost() : encounter.getEnemy());
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
      description.append("\n").append(damageInflicted.buildDamageDescription());
    }
    description.append("\n\n").append(buildBattleStatsSummary());
    return description;
  }

  private String buildBattleStatsSummary()
  {
    return "Stats after round " + encounter.getBattleRound() + ":\n" +
            "—————————" +
            "\n" + buildCharacterSummaryLine(encounter.getHost().getName(), encounter.getHost().getStats()) +
            "\n" + buildCharacterSummaryLine(encounter.getEnemy().name, encounter.getEnemy().getStats());
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
