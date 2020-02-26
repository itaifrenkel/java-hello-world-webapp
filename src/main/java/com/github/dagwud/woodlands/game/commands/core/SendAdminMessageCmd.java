package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.Settings;

public class SendAdminMessageCmd extends SendMessageCmd
{
  public SendAdminMessageCmd(String message)
  {
    super(Settings.ADMIN_CHAT, message);
  }
}
