package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Player;

public class NoActiveCharacterPrerequisite implements CommandPrerequisite
{
  private final Player player;

  NoActiveCharacterPrerequisite(Player player)
  {
    this.player = player;
  }

  @Override
  public boolean verify()
  {
    if (player != null && player.getActiveCharacter() != null && player.getActiveCharacter().isSetupComplete() && player.getActiveCharacter().getStats().getState() != EState.DEAD)
    {
      SendMessageCmd cmd = new SendMessageCmd(player.getChatId(), "You already have an active character - you're " + player.getActiveCharacter().getName() + " the " + player.getActiveCharacter().getCharacterClass());
      CommandDelegate.execute(cmd);
      return false;
    }
    return true;

  }
}
