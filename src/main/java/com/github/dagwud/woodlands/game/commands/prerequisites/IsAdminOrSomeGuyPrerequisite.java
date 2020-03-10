package com.github.dagwud.woodlands.game.commands.prerequisites;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.CommandPrerequisite;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;

public class IsAdminOrSomeGuyPrerequisite implements CommandPrerequisite
{
  private final int chatId;

  public IsAdminOrSomeGuyPrerequisite(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public boolean verify()
  {
    if (chatId != Settings.ADMIN_CHAT && chatId != -1 && chatId != Settings.NOT_THE_ADMIN_JUST_SOME_OTHER_GUY_CHAT)
    {
      SendMessageCmd notAdmin = new SendMessageCmd(chatId, "You're not an admin or some other strangely specific guy. Go away.");
      CommandDelegate.execute(notAdmin);
      return false;
    }

    return true;
  }
}
