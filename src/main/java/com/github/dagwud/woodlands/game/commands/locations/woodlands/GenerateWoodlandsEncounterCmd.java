package com.github.dagwud.woodlands.game.commands.locations.woodlands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.GenerateEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;

public class GenerateWoodlandsEncounterCmd extends GenerateEncounterCmd
{
  private static final long serialVersionUID = 1L;

  GenerateWoodlandsEncounterCmd(PlayerState playerState)
  {
    super(playerState, ELocation.WOODLANDS, Settings.WOODLANDS_MIN_DIFFICULTY, Settings.WOODLANDS_MAX_DIFFICULTY);
  }

  public void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GenerateWoodlandsEncounterCmd(getPlayerState()));
    CommandDelegate.execute(nextEncounter);
  }
}
