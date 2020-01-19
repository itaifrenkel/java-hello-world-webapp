package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.battle.DealDamageCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

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
    if (encounter.getEnemy().getStats().getState() == EState.ALIVE)
    {
      doAttack(encounter.getHost(), encounter.getHost().getCarrying().getCarriedLeft(), encounter.getEnemy());
    }
    if (encounter.getEnemy().getStats().getState() == EState.ALIVE)
    {
      doAttack(encounter.getHost(), encounter.getHost().getCarrying().getCarriedRight(), encounter.getEnemy());
    }

    if (encounter.getEnemy().getStats().getState() == EState.ALIVE)
    {
      scheduleNextRound();
    }
    else
    {
      encounter.end();
      SendMessageCmd cmd = new SendMessageCmd(chatId, encounter.getEnemy().name + " has been defeated!");
      CommandDelegate.execute(cmd);
    }
  }

  private void doAttack(GameCharacter attacker, Weapon attackWith, Creature defender)
  {
    String description = "⚔️ Battle Round #" + encounter.getBattleRound() + ": ⚔️\n" +
            "———————————\n";
    AttackCmd attack = new AttackCmd(attacker, attackWith, defender);
    CommandDelegate.execute(attack);
    DamageInflicted hostDamageInflicted = attack.getDamageInflicted();

    DealDamageCmd cmd = new DealDamageCmd(hostDamageInflicted, defender);
    CommandDelegate.execute(cmd);
    description += hostDamageInflicted.buildDamageDescription();

    description += "\n\n" + buildBattleStatsSummary();

    SendMessageCmd status = new SendMessageCmd(chatId, description);
    CommandDelegate.execute(status);
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
