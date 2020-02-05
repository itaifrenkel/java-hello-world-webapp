package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.GenerateEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;

public class GenerateMountainEncounterCmd extends GenerateEncounterCmd
{
  private static final long serialVersionUID = 1L;

  GenerateMountainEncounterCmd(PlayerState playerState)
  {
    super(playerState, ELocation.MOUNTAIN, Settings.MOUNTAIN_MIN_DIFFICULTY, Settings.MOUNTAIN_MAX_DIFFICULTY);
  }

  @Override
  protected void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GenerateMountainEncounterCmd(getPlayerState()));
    CommandDelegate.execute(nextEncounter);
  }
}
