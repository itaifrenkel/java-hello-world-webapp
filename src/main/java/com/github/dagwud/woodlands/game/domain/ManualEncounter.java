package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.battle.EncounterRoundCmd;
import com.github.dagwud.woodlands.game.commands.battle.ManualEncounterRoundCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;

import java.util.List;

public class ManualEncounter extends Encounter
{
  private static final long serialVersionUID = 1L;
  private final long timeAllowedForPlanningMS;

  public ManualEncounter(Party party, List<? extends Fighter> enemies, int timeAllowedForPlanningMS, int actionsAllowedPerRound)
  {
    super(party, enemies, actionsAllowedPerRound);
    this.timeAllowedForPlanningMS = timeAllowedForPlanningMS;
  }

  @Override
  public void startFighting()
  {
    if (!hasAnyPlayerActivityPrepared())
    {
      CommandDelegate.execute(new SendPartyMessageCmd(getParty(), "Nobody is keen for a fight"));
      CommandDelegate.execute(new MoveToLocationCmd(getParty().getLeader(), ELocation.VILLAGE_SQUARE));
    }
  }

  @Override
  public EncounterRoundCmd createNextRoundCmd(PlayerState playerState, int delayBetweenRoundsMS)
  {
    return new ManualEncounterRoundCmd(playerState, delayBetweenRoundsMS);
  }

  public final long getTimeAllowedForPlanningMS()
  {
    return timeAllowedForPlanningMS;
  }
}
