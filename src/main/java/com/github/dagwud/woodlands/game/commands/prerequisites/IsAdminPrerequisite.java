package com.github.dagwud.woodlands.game.commands.prerequisites;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;

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
    if (chatId != Settings.ADMIN_CHAT)
    {
      SendMessageCmd notAdmin = new SendMessageCmd(chatId, "You're not an admin. Go away.");
      CommandDelegate.execute(notAdmin);
      return false;
    }
    return true;
  }
}
