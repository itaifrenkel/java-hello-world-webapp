package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.battle.ChooseConsciousCharacterCmd;
import com.github.dagwud.woodlands.game.commands.battle.EncounterRoundCmd;
import com.github.dagwud.woodlands.game.commands.battle.ManualEncounterRoundCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;

public class ManualEncounter extends Encounter
{
  private static final long serialVersionUID = 1L;
  private final long timeAllowedForPlanningMS;

  public ManualEncounter(FightingGroup aggressor, FightingGroup enemies, int timeAllowedForPlanningMS, int actionsAllowedPerRound, ELocation location)
  {
    super(aggressor, enemies, actionsAllowedPerRound, location);
    this.timeAllowedForPlanningMS = timeAllowedForPlanningMS;
  }

  @Override
  public void startFighting()
  {
    if (!hasAnyPlayerActivityPrepared())
    {
      CommandDelegate.execute(new SendPartyMessageCmd(getAggressor(), "Nobody is keen for a fight"));
      ChooseConsciousCharacterCmd retreat = new ChooseConsciousCharacterCmd(getAggressor());
      CommandDelegate.execute(retreat);
      if (retreat.getChosen() == null)
      {
        throw new WoodlandsRuntimeException("Nobody is alive to sound the retreat!");
      }
      CommandDelegate.execute(new MoveToLocationCmd(retreat.getChosen(), ELocation.VILLAGE_SQUARE));
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
