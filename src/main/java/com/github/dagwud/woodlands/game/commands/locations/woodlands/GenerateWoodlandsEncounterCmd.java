package com.github.dagwud.woodlands.game.commands.locations.woodlands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.GenerateManualEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.gson.game.Creature;

public class GenerateWoodlandsEncounterCmd extends GenerateManualEncounterCmd
{
  private static final long serialVersionUID = 1L;

  GenerateWoodlandsEncounterCmd(PlayerState playerState)
  {
    super(playerState, ELocation.WOODLANDS, 1, Settings.WOODLANDS_MIN_DIFFICULTY,
            Settings.WOODLANDS_MAX_DIFFICULTY, Creature.CREATURE_TYPE_NORMAL,
            Settings.WOODLANDS_TIME_ALLOWED_FOR_PLANNING_MS, Settings.WOODLANDS_ACTIONS_PER_ROUND);
  }

  @Override
  public void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GenerateWoodlandsEncounterCmd(getPlayerState()));
    CommandDelegate.execute(nextEncounter);
  }
}
