package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunScheduledCmd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Scheduler implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Collection<RunLaterCmd> scheduledCommands;

  public static Scheduler instance()
  {
    return GameStatesRegistry.instance().getScheduler();
  }

  private Collection<RunLaterCmd> getScheduledCommands()
  {
    if (null == scheduledCommands)
    {
      scheduledCommands = new ArrayList<>();
    }
    return scheduledCommands;
  }

  public void restoreScheduled()
  {
    for (RunLaterCmd scheduledCommand : getScheduledCommands())
    {
      CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Restoring scheduled command: " + scheduledCommand.toString()));
      doSchedule(scheduledCommand);
    }
  }

  public void schedule(RunLaterCmd cmd)
  {
    getScheduledCommands().add(cmd);
    doSchedule(cmd);
  }

  private void doSchedule(RunLaterCmd cmd)
  {
    Callable<String> callable = new RunScheduledCmd(cmd.getDelayMS(), cmd.getCmdToRun());
    FutureTask task = new FutureTask<>(callable);
    // Yes, threads are forbidden in EJB... but the current deployment server isn't actually
    // using an EJB container, so this seems ok.
    new Thread(task).start();
    CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, "Scheduled: " + cmd.toString()));
  }

  public void clear()
  {
    getScheduledCommands().clear();
  }

  public int count()
  {
    return getScheduledCommands().size();
  }
}
