package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.IsAdminPrerequisite;

public abstract class AdminCmd extends AbstractCmd
{
  public AdminCmd(int chatId)
  {
    super(new IsAdminPrerequisite(chatId));
  }
}
