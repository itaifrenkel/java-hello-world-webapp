package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunScheduledCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
    Collection<RunLaterCmd> scheduledCommands = new ArrayList<>(getScheduledCommands());
    for (RunLaterCmd scheduledCommand : scheduledCommands)
    {
      if (scheduledCommand.isRestore())
      {
        doSchedule(scheduledCommand);
      }
    }
  }

  public void schedule(RunLaterCmd cmd)
  {
    getScheduledCommands().add(cmd);
    doSchedule(cmd);
  }

  private void doSchedule(RunLaterCmd cmd)
  {
    long delayMS = cmd.getRemainingDelayMS();
    Callable<String> callable = new RunScheduledCmd(delayMS, cmd.getCmdToRun());
    FutureTask<String> task = new FutureTask<>(callable);

    // Yes, threads are forbidden in EJB... but the current deployment server isn't actually
    // using an EJB container, so this seems ok.
    new Thread(task).start();
  }

  public void onComplete(AbstractCmd complete)
  {
    Iterator<RunLaterCmd> it = getScheduledCommands().iterator();

    while (it.hasNext())
    {
      RunLaterCmd run = it.next();
      if (run.getCmdToRun() == complete)
      {
        it.remove();
        return;
      }
    }

    CommandDelegate.execute(new SendAdminMessageCmd("Schedule oncomplete not found: " + complete.toString()));
  }

  public void clear()
  {
    getScheduledCommands().clear();
  }

  public int count()
  {
    return getScheduledCommands().size();
  }

  public Collection<String> listScheduleDescriptions()
  {
    Collection<String> list = new ArrayList<>();
    for (RunLaterCmd scheduled : getScheduledCommands())
    {
      list.add(scheduled.getCmdToRun().toString() + " - " + scheduled.getRemainingDelayMS() + "ms");
    }
    return list;
  }
}
