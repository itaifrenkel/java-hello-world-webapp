package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.mountain.EnterTheMountainCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class MoveToLocationCmd extends AbstractCmd
{
  private final PlayerState playerState;
  private final ELocation location;

  public MoveToLocationCmd(PlayerState playerState, ELocation location)
  {
    this.playerState = playerState;
    this.location = location;
  }

  @Override
  public void execute()
  {
    GameCharacter characterToMove = playerState.getActiveCharacter();
    int chatId = playerState.getPlayer().getChatId();

    if (playerState.getActiveEncounter() != null)
    {
      playerState.getActiveEncounter().end();
      playerState.setActiveEncounter(null);
    }

    if (!characterToMove.isSetupComplete())
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId,"You need to create a character first. Please use /new");
      CommandDelegate.execute(cmd);
      return;
    }

    characterToMove.setLocation(location);
    SendMessageCmd cmd = new SendMessageCmd(chatId, "You are now at " + characterToMove.getLocation());
    CommandDelegate.execute(cmd);

    showMenuForLocation(location, playerState);
    handleLocationEntry(location, playerState);
  }

  private void showMenuForLocation(ELocation location, PlayerState playerState)
  {
    ShowMenuCmd cmd = new ShowMenuCmd(location.getMenu(), playerState);
    CommandDelegate.execute(cmd);
  }

  private void handleLocationEntry(ELocation location, PlayerState playerState)
  {
    if (location == ELocation.MOUNTAIN)
    {
      EnterTheMountainCmd cmd = new EnterTheMountainCmd(playerState);
      CommandDelegate.execute(cmd);
    }
  }
}
