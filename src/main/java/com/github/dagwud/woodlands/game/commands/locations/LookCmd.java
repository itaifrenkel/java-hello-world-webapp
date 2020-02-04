package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class LookCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final ELocation location;
  private final int chatId;
  private final PlayerState playerState;

  public LookCmd(int chatId, PlayerCharacter character)
  {
    this.chatId = chatId;
    this.location = character.getLocation();
    this.playerState = character.getPlayedBy().getPlayerState();
  }

  @Override
  public void execute()
  {
    SendMessageCmd cmd = new SendMessageCmd(chatId, location.getLookText());
    CommandDelegate.execute(cmd);

    ShowMenuCmd showMenuCmd = new ShowMenuCmd(location.getMenu(), playerState);
    CommandDelegate.execute(showMenuCmd);
  }
}
