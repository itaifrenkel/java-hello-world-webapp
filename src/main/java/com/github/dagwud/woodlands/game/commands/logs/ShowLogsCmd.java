package com.github.dagwud.woodlands.game.commands.logs;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.log.Logger;

public class ShowLogsCmd extends AbstractCmd
{
  private final int chatId;

  public ShowLogsCmd(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    for (String log : Logger.getLogs())
    {
      SendMessageCmd msg = new SendMessageCmd(chatId, log);
      CommandDelegate.execute(msg);
    }
  }
}
