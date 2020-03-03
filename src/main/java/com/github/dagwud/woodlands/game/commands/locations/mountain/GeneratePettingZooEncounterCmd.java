package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.GenerateAutomaticEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.gson.game.Creature;

public class GeneratePettingZooEncounterCmd extends GenerateAutomaticEncounterCmd
{
  private static final long serialVersionUID = 1L;

  GeneratePettingZooEncounterCmd(PlayerState playerState)
  {
    super(playerState, ELocation.PETTING_ZOO, 3,
            Settings.PETTING_ZOO_MIN_DIFFICULTY, Settings.PETTING_ZOO_MAX_DIFFICULTY,
            Creature.CREATURE_TYPE_NORMAL);
  }

  @Override
  protected void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS, new GeneratePettingZooEncounterCmd(getPlayerState()));
    CommandDelegate.execute(nextEncounter);
  }
}
