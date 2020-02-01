package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class LookCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final ELocation location;
  private final int chatId;

  public LookCmd(int chatId, PlayerCharacter character)
  {
    this.chatId = chatId;
    this.location = character.getLocation();
  }

  @Override
  public void execute() throws Exception
  {
    SendMessageCmd cmd = new SendMessageCmd(chatId, location.getLookText());
    CommandDelegate.execute(cmd);
  }
}
