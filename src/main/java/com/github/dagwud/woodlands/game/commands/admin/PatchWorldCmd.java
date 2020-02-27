package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.*;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.commands.start.CreateDefaultSchedulesCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.ArrayList;
import java.util.Collection;

public class PatchWorldCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  @Override
  public void execute()
  {
    Collection<PlayerState> playerStates = new ArrayList<>(GameStatesRegistry.allPlayerStates());
    for (PlayerState playerState : playerStates)
    {
      patch(playerState.getPlayer().getActiveCharacter());

      for (PlayerCharacter inactiveCharacter : playerState.getPlayer().getInactiveCharacters())
      {
        patch(inactiveCharacter);
      }
    }

    if (Scheduler.instance().count() == 0)
    {
      CommandDelegate.execute(new SendAdminMessageCmd("Schedules appear to have been lost! Recreating them..."));
      recreateSchedules();
    }
  }

  private void recreateSchedules()
  {
    for (PlayerState playerState : GameStatesRegistry.allPlayerStates())
    {
      CommandDelegate.execute(new CreateDefaultSchedulesCmd(playerState.getActiveCharacter()));
    }

    // Rest and Drink completion schedules are lost:
    for (PlayerState playerState : GameStatesRegistry.allPlayerStates())
    {
      PlayerCharacter character = playerState.getActiveCharacter();
      if (character.isResting() || character.isDrinking())
      {
        character.getStats().setState(EState.ALIVE);
        CommandDelegate.execute(new MoveToLocationCmd(character, ELocation.VILLAGE_SQUARE));
        CommandDelegate.execute(new SendAdminMessageCmd("Unrested " + character.getName()));
      }
    }
  }

  private void patch(PlayerCharacter character)
  {
    CommandDelegate.execute(new PatchCharacterCmd(character));
  }
}
