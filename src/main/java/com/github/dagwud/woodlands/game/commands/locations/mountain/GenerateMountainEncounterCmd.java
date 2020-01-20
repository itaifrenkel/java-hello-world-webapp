package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.*;
import com.github.dagwud.woodlands.game.commands.creatures.SpawnCreatureCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;

public class GenerateMountainEncounterCmd extends AbstractCmd
{
  private final PlayerState playerState;

  GenerateMountainEncounterCmd(PlayerState playerState)
  {
    this.playerState = playerState;
  }

  @Override
  public void execute()
  {
    // You've left the mountain; encounter no longer happens:
    if (playerState.getActiveCharacter().getLocation() != ELocation.MOUNTAIN)
    {
      return;
    }

    // There's already an encounter in progress; don't start another one:
    if (playerState.getActiveCharacter().getParty().getActiveEncounter() != null)
    {
      if (playerState.getActiveCharacter().getParty().getActiveEncounter().isEnded())
      {
        playerState.getActiveCharacter().getParty().setActiveEncounter(null);
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
      SendPartyMessageCmd cmd = new SendPartyMessageCmd(playerState.getPlayer().getActiveCharacter().getParty(), "Time passes. You keep moving. Nothing interesting happens.");
      CommandDelegate.execute(cmd);
      scheduleNextEncounter();
      return;
    }

    Encounter encounter = startEncounter();
    playerState.getActiveCharacter().getParty().setActiveEncounter(encounter);

    EncounterRoundCmd cmd = new EncounterRoundCmd(playerState.getPlayer().getChatId(), encounter, Settings.DELAY_BETWEEN_ROUNDS_MS);
    CommandDelegate.execute(cmd);

    scheduleNextEncounter();
  }

  private Encounter startEncounter()
  {
    Encounter encounter = createEncounter(playerState.getActiveCharacter().getParty());

    String message = "You encountered a " + encounter.getEnemy().name + " (L" + encounter.getEnemy().difficulty + ")";
    SendPartyMessageCmd msg = new SendPartyMessageCmd(playerState.getActiveCharacter().getParty(), message);
    CommandDelegate.execute(msg);
    return encounter;
  }

  private Encounter createEncounter(Party party)
  {
    SpawnCreatureCmd cmd = new SpawnCreatureCmd();
    CommandDelegate.execute(cmd);
    Creature creature = cmd.getSpawnedCreature();

    Encounter encounter = new Encounter();
    encounter.setParty(party);
    encounter.setEnemy(creature);
    return encounter;
  }

  private void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GenerateMountainEncounterCmd(playerState));
    CommandDelegate.execute(nextEncounter);
  }
}
