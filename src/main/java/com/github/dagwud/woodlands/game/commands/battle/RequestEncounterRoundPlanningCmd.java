package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.domain.ManualEncounter;
import com.github.dagwud.woodlands.game.domain.menu.ManualEncounterMenu;

public class RequestEncounterRoundPlanningCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final ManualEncounter encounter;
  private final PlayerState playerState;

  RequestEncounterRoundPlanningCmd(PlayerState playerState, ManualEncounter encounter)
  {
    this.playerState = playerState;
    this.encounter = encounter;
  }

  @Override
  public void execute()
  {
    if (encounter.isEnded())
    {
      return;
    }

    promptForAction();
  }

  private void promptForAction()
  {
    String planningDuration = encounter.getTimeAllowedForPlanningMS() / 1000 + " seconds";
    ManualEncounterMenu menu = new ManualEncounterMenu(planningDuration, encounter.getParty().getLeader().getLocation());
    playerState.setCurrentMenu(menu);
    ShowMenuCmd showMenuCmd = new ShowMenuCmd(menu, playerState);
    CommandDelegate.execute(showMenuCmd);
  }

}
