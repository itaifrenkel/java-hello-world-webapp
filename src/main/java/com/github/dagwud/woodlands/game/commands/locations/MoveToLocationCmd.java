package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.mountain.EnterTheMountainCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class MoveToLocationCmd extends AbstractCmd
{
  private GameState gameState;
  private final ELocation location;

  public MoveToLocationCmd(GameState gameState, ELocation location)
  {
    this.gameState = gameState;
    this.location = location;
  }

  @Override
  public void execute()
  {
    GameCharacter characterToMove = gameState.getActiveCharacter();
    int chatId = gameState.getPlayer().getChatId();

    if (!characterToMove.isSetupComplete())
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId,"You need to create a character first. Please use /new");
      CommandDelegate.execute(cmd);
      return;
    }

    characterToMove.setLocation(location);
    SendMessageCmd cmd = new SendMessageCmd(chatId, "You are now at " + characterToMove.getLocation());
    CommandDelegate.execute(cmd);

    showMenuForLocation(location, gameState);
    handleLocationEntry(location, gameState);
  }

  private void showMenuForLocation(ELocation location, GameState gameState)
  {
    ShowMenuCmd cmd = new ShowMenuCmd(location.getMenu(), gameState);
    CommandDelegate.execute(cmd);
  }

  private void handleLocationEntry(ELocation location, GameState gameState)
  {
    if (location == ELocation.MOUNTAIN)
    {
      EnterTheMountainCmd cmd = new EnterTheMountainCmd(gameState);
      CommandDelegate.execute(cmd);
    }
  }
}
