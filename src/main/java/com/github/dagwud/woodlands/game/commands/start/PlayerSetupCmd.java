package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.Player;

public class PlayerSetupCmd extends AbstractCmd
{
  private final Player player;

  public PlayerSetupCmd(Player player)
  {
    this.player = player;
  }

  @Override
  public void execute()
  {
    if (player.getActiveCharacter().isSetupComplete())
    {
      SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), "You already have an active character - you're " + player.getActiveCharacter().getName() + " the " + player.getActiveCharacter().getCharacterClass());
      CommandDelegate.execute(cmd);
    }
    else
    {
      DoPlayerSetupCmd cmd = new DoPlayerSetupCmd(player);
      CommandDelegate.execute(cmd);
    }
  }
}
