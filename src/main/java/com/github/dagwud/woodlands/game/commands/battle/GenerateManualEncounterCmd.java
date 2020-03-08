package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.ManualEncounter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.util.List;

public abstract class GenerateManualEncounterCmd extends GenerateEncounterCmd
{
  private static final long serialVersionUID = 1L;
  private final int timeAllowedForPlanningMS;
  private final int actionsPerRound;

  public GenerateManualEncounterCmd(PlayerState playerState, ELocation location, int enemyCount, double minDifficulty, double maxDifficulty, String creatureType, int timeAllowedForPlanningMS, int actionsPerRound)
  {
    super(playerState, location, enemyCount, minDifficulty, maxDifficulty, creatureType);
    this.timeAllowedForPlanningMS = timeAllowedForPlanningMS;
    this.actionsPerRound = actionsPerRound;
  }

  @Override
  protected boolean shouldHaveEncounter()
  {
    return true;
  }

  @Override
  protected Encounter createEncounter(Party party, List<? extends Fighter> enemy)
  {
    return new ManualEncounter(party, enemy, timeAllowedForPlanningMS, actionsPerRound);
  }

  @Override
  protected void scheduleFirstRound(Encounter encounter)
  {
    EncounterRoundCmd cmd = new ManualEncounterRoundCmd(getPlayerState(), Settings.DELAY_BETWEEN_ROUNDS_MS);
    CommandDelegate.execute(cmd);
  }

  protected int getTimeAllowedForPlanningMS()
  {
    return timeAllowedForPlanningMS;
  }

  protected int getActionsPerRound()
  {
    return actionsPerRound;
  }
}
