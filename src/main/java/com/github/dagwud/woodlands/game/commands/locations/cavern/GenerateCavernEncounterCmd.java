package com.github.dagwud.woodlands.game.commands.locations.cavern;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.battle.GenerateAutomaticEncounterCmd;
import com.github.dagwud.woodlands.game.commands.battle.GenerateManualEncounterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.gson.game.Creature;

public class GenerateCavernEncounterCmd extends GenerateManualEncounterCmd
{
  private static final long serialVersionUID = 1L;

  GenerateCavernEncounterCmd(PlayerState playerState, ELocation cavernLocation, int enemyCount, double minDifficulty, double maxDifficulty)
  {
    super(playerState, cavernLocation, enemyCount,
            minDifficulty, maxDifficulty,
            Creature.CREATURE_TYPE_NORMAL,
            Settings.CAVERN_TIME_ALLOWED_FOR_PLANNING_MS,
            Settings.CAVERN_ACTIONS_PER_ROUND);
  }

  @Override
  protected void scheduleNextEncounter()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(Settings.DELAY_BETWEEN_ENCOUNTERS_MS,
            new GenerateCavernEncounterCmd(getPlayerState(), getLocation(), getEnemyCount(), getMinDifficulty(), getMaxDifficulty()));
    CommandDelegate.execute(nextEncounter);
  }
}
