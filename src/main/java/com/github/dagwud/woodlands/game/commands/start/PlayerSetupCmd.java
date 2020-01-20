package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;

public class PlayerSetupCmd extends AbstractCmd
{
  private final PlayerState playerState;

  public PlayerSetupCmd(PlayerState playerState)
  {
    this.playerState = playerState;
  }

  @Override
  public void execute()
  {
    if (playerState.getActiveCharacter().isSetupComplete())
    {
      SendMessageCmd cmd = new SendMessageCmd(playerState.getPlayer().getChatId(), "You already have an active character - you're " + playerState.getActiveCharacter().getName() + " the " + playerState.getActiveCharacter().getCharacterClass());
      CommandDelegate.execute(cmd);
    }
    else
    {
      DoPlayerSetupCmd cmd = new DoPlayerSetupCmd(playerState);
      CommandDelegate.execute(cmd);
    }
  }
}
