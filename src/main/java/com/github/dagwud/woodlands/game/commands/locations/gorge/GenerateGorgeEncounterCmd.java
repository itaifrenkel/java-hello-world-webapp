package com.github.dagwud.woodlands.game.commands.locations.gorge;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.GenerateEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.gson.game.Creature;

public class GenerateGorgeEncounterCmd extends GenerateEncounterCmd
{
  private static final long serialVersionUID = 1L;

  GenerateGorgeEncounterCmd(PlayerState playerState)
  {
    super(playerState, ELocation.THE_GORGE, Settings.THE_GORGE_MIN_DIFFICULTY, Settings.THE_GORGE_MAX_DIFFICULTY, Creature.CREATURE_TYPE_DRAGON);
  }

  public void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GenerateGorgeEncounterCmd(getPlayerState()));
    CommandDelegate.execute(nextEncounter);
  }
}
