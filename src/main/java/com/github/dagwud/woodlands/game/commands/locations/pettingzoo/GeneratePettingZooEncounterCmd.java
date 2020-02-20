package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.GenerateEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.gson.game.Creature;

public class GeneratePettingZooEncounterCmd extends GenerateEncounterCmd
{
  private static final long serialVersionUID = 1L;

  GeneratePettingZooEncounterCmd(PlayerState playerState)
  {
    super(playerState, ELocation.MOUNTAIN, Settings.PETTING_ZOO_MIN_DIFFICULTY, Settings.PETTING_ZOO_MAX_DIFFICULTY, Creature.CREATURE_TYPE_NORMAL);
  }

  @Override
  protected void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GeneratePettingZooEncounterCmd(getPlayerState()));
    CommandDelegate.execute(nextEncounter);
  }
}
