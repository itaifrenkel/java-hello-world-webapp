package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.RunScheduledCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Scheduler implements Serializable
{
  private static final long serialVersionUID = 1L;
  private transient Collection<RunLaterCmd> scheduledCommands;

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
    List<RunLaterCmd> notPersistentCommands = new ArrayList<>();

    for (RunLaterCmd scheduledCommand : scheduledCommands)
    {
      if (scheduledCommand.isRestore())
      {
        doSchedule(scheduledCommand);
      }
      else
      {
        notPersistentCommands.add(scheduledCommand);
      }
    }

    getScheduledCommands().removeAll(notPersistentCommands);
  }

  public void schedule(RunLaterCmd cmd)
  {
    if (cmd.isRestore())
    {
      getScheduledCommands().add(cmd);
    }

    doSchedule(cmd);
  }

  private void doSchedule(RunLaterCmd cmd)
  {
    long delayMS = cmd.getRemainingDelayMS();
    Callable<String> callable = new RunScheduledCmd(delayMS, cmd.getCmdToRun(), cmd.isRestore());
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
    List<String> list = new ArrayList<>();
    for (RunLaterCmd scheduled : getScheduledCommands())
    {
      String delay = (int) (Math.floorDiv(scheduled.getRemainingDelayMS(), 1000)) + "s";
      list.add(scheduled.getCmdToRun().toString() + " - " + delay);
    }

    Collections.sort(list);
    return list;
  }
}
