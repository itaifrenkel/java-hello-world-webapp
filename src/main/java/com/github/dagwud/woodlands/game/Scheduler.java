package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.CommandDelegate;
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
      schedule(scheduledCommand);
    }
  }

  public void schedule(RunLaterCmd cmd)
  {
    if (!getScheduledCommands().contains(cmd))
    {
      getScheduledCommands().add(cmd);
      CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT_ID, "Scheduled: " + cmd.toString()));
    }

    Callable<String> callable = new RunScheduledCmd(cmd.getDelayMS(), cmd.getCmdToRun());
    FutureTask task = new FutureTask<>(callable);
    // Yes, threads are forbidden in EJB... but the current deployment server isn't actually
    // using an EJB container, so this seems ok.
    new Thread(task).start();
  }
}
