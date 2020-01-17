package com.github.dagwud.woodlands.game.instructions;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class RunLaterCmd extends AbstractCmd
{
  private final long delayMS;
  private final AbstractCmd cmdToRun;

  RunLaterCmd(long delayMS, AbstractCmd cmdToRun)
  {
    this.delayMS = delayMS;
    this.cmdToRun = cmdToRun;
  }

  @Override
  public void execute()
  {
    Callable<String> callable = new RunScheduledCmd(delayMS, cmdToRun);
    FutureTask task = new FutureTask<>(callable);
    // Yes, threads are forbidden in EJB... but the current deployment server isn't actually
    // using an EJB container, so this seems ok.
    new Thread(task).start();
  }
}
