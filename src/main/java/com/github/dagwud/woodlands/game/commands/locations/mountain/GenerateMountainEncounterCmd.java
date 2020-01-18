package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.creatures.CreaturesCacheFactory;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.math.BigDecimal;
import java.util.Iterator;

public class GenerateMountainEncounterCmd extends AbstractCmd
{
  private final GameState gameState;

  GenerateMountainEncounterCmd(GameState gameState)
  {
    this.gameState = gameState;
  }

  @Override
  public void execute()
  {
    // You've left the mountain; encounter no longer happens:
    if (gameState.getActiveCharacter().getLocation() != ELocation.MOUNTAIN)
    {
      return;
    }

    ChanceCalculatorCmd chance = new ChanceCalculatorCmd(new BigDecimal("75"));
    CommandDelegate.execute(chance);

    if (!chance.getResult())
    {
      SendMessageCmd cmd = new SendMessageCmd(gameState.getPlayer().getChatId(), "Time passes. You keep moving. Nothing interesting happens.");
      CommandDelegate.execute(cmd);
      scheduleNextEncounter();
      return;
    }

    SendMessageCmd cmd = new SendMessageCmd(gameState.getPlayer().getChatId(), "Something happens!");
    CommandDelegate.execute(cmd);

    Creature creature = CreaturesCacheFactory.instance().getCache().pickRandom();
    String message = "You encountered a " + creature.name + " (L" + creature.level + ")";
    SendMessageCmd msg = new SendMessageCmd(gameState.getPlayer().getChatId(), message);
    CommandDelegate.execute(msg);

    gameState.getActiveCharacter().getStats().setLevel(gameState.getActiveCharacter().getStats().getLevel() + 1);

    scheduleNextEncounter();
  }

  private void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(30000, new GenerateMountainEncounterCmd(gameState));
    CommandDelegate.execute(nextEncounter);
  }
}
