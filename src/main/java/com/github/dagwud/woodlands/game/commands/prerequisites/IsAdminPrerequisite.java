package com.github.dagwud.woodlands.game.commands.prerequisites;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;

public class IsAdminPrerequisite implements CommandPrerequisite
{
  private final int chatId;

  public IsAdminPrerequisite(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public boolean verify()
  {
    return chatId == Settings.ADMIN_CHAT;
  }
}
