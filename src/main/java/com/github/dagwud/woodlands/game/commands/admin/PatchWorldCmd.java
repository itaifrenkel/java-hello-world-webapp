package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.*;
import com.github.dagwud.woodlands.game.commands.character.PeriodicSoberUpCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
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
      recreateSchedules();
    }
  }

  private void recreateSchedules()
  {
    CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Schedules appear to have been lost! Recreating them..."));
    for (PlayerState playerState : GameStatesRegistry.allPlayerStates())
    {
      createSchedules(playerState.getActiveCharacter());
      for (PlayerCharacter inactiveCharacter : playerState.getPlayer().getInactiveCharacters())
      {
        createSchedules(inactiveCharacter);
      }

      if (playerState.getActiveCharacter().isResting())
      {
        PlayerCharacter activeCharacter = playerState.getActiveCharacter();
        activeCharacter.getStats().setState(EState.ALIVE);
        CommandDelegate.execute(new MoveToLocationCmd(activeCharacter, ELocation.VILLAGE_SQUARE));
        CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Unrested " + activeCharacter.getName()));
      }
    }
  }

  private void createSchedules(PlayerCharacter character)
  {
    PeriodicSoberUpCmd periodicSoberUp = new PeriodicSoberUpCmd(character, character.getPlayedBy().getChatId());
    CommandDelegate.execute(periodicSoberUp);
  }

  private void patch(PlayerCharacter character)
  {
    CommandDelegate.execute(new PatchCharacterCmd(character));
  }
}
