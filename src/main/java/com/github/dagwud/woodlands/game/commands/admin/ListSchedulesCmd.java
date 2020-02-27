package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Scheduler;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;

public class ListSchedulesCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  public ListSchedulesCmd(int chatId)
  {
    super(chatId);
  }

  @Override
  public void execute()
  {
    for (String schedule : Scheduler.instance().listScheduleDescriptions())
    {
      CommandDelegate.execute(new SendAdminMessageCmd(schedule));
    }
  }
}
