package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameState;

public class PlayerSetupCmd extends AbstractCmd
{
  private final GameState gameState;

  public PlayerSetupCmd(GameState gameState)
  {
    this.gameState = gameState;
  }

  @Override
  public void execute()
  {
    if (gameState.getActiveCharacter().isSetupComplete())
    {
      SendMessageCmd cmd = new SendMessageCmd(gameState.getPlayer().getChatId(), "You already have an active character - you're " + gameState.getActiveCharacter().getName() + " the " + gameState.getActiveCharacter().getCharacterClass());
      CommandDelegate.execute(cmd);
    }
    else
    {
      DoPlayerSetupCmd cmd = new DoPlayerSetupCmd(gameState);
      CommandDelegate.execute(cmd);
    }
  }
}
