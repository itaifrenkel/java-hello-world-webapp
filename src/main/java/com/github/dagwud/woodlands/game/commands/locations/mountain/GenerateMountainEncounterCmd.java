package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.GenerateAutomaticEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.gson.game.Creature;

public class GenerateMountainEncounterCmd extends GenerateAutomaticEncounterCmd
{
  private static final long serialVersionUID = 1L;

  GenerateMountainEncounterCmd(PlayerState playerState)
  {
    super(playerState, ELocation.MOUNTAIN, 1,
            Settings.MOUNTAIN_MIN_DIFFICULTY, Settings.MOUNTAIN_MAX_DIFFICULTY,
            Creature.CREATURE_TYPE_NORMAL);
  }

  @Override
  protected void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GenerateMountainEncounterCmd(getPlayerState()));
    CommandDelegate.execute(nextEncounter);
  }
}
