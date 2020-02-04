package com.github.dagwud.woodlands.game.commands.logs;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.admin.AdminCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.log.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShowLogsCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;

  public ShowLogsCmd(int chatId)
  {
    super(chatId);
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    List<String> dump = new ArrayList<>(Logger.getLogs());
    for (String log : dump)
    {
      SendMessageCmd msg = new SendMessageCmd(chatId, log);
      CommandDelegate.execute(msg);
    }
    Logger.clear();
    SendMessageCmd msg = new SendMessageCmd(chatId, "---end---");
    CommandDelegate.execute(msg);
  }
}
