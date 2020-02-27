package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.messaging.MessagingFactory;

public class SendAdminMessageCmd extends AbstractCmd
{
  private final String message;

  public SendAdminMessageCmd(String message)
  {
    this.message = message;
  }

  @Override
  public void execute()
  {
    // best attempt only because users can block bot:
    try
    {
      MessagingFactory.create().sender().sendMessage(Settings.ADMIN_CHAT, message);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
