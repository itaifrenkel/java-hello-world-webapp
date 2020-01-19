package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ChanceCalculatorCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.creatures.SpawnCreatureCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;

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

    // There's already an encounter in progress; don't start another one:
    if (gameState.getActiveEncounter() != null)
    {
      if (gameState.getActiveEncounter().isEnded())
      {
        gameState.setActiveEncounter(null);
      }
      else
      {
        scheduleNextEncounter();
        return;
      }
    }

    ChanceCalculatorCmd chance = new ChanceCalculatorCmd(Settings.PERCENT_CHANGE_OF_ENCOUNTER);
    CommandDelegate.execute(chance);

    if (!chance.getResult())
    {
      SendMessageCmd cmd = new SendMessageCmd(gameState.getPlayer().getChatId(), "Time passes. You keep moving. Nothing interesting happens.");
      CommandDelegate.execute(cmd);
      scheduleNextEncounter();
      return;
    }

    Encounter encounter = startEncounter();
    gameState.setActiveEncounter(encounter);

    EncounterRoundCmd cmd = new EncounterRoundCmd(gameState.getPlayer().getChatId(), encounter, Settings.DELAY_BETWEEN_ROUNDS_MS);
    CommandDelegate.execute(cmd);

    scheduleNextEncounter();
  }

  private Encounter startEncounter()
  {
    Encounter encounter = createEncounter(gameState.getActiveCharacter());

    String message = "You encountered a " + encounter.getEnemy().name + " (L" + encounter.getEnemy().difficulty + ")";
    SendMessageCmd msg = new SendMessageCmd(gameState.getPlayer().getChatId(), message);
    CommandDelegate.execute(msg);
    return encounter;
  }

  private Encounter createEncounter(GameCharacter host)
  {
    SpawnCreatureCmd cmd = new SpawnCreatureCmd();
    CommandDelegate.execute(cmd);
    Creature creature = cmd.getSpawnedCreature();

    Encounter encounter = new Encounter();
    encounter.setHost(host);
    encounter.setEnemy(creature);
    return encounter;
  }

  private void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GenerateMountainEncounterCmd(gameState));
    CommandDelegate.execute(nextEncounter);
  }
}
