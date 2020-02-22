package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.Scheduler;

public class RunLaterCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private long startedAtTime;
  private final long delayMS;
  private final AbstractCmd cmdToRun;
  private boolean restore;

  public RunLaterCmd(long delayMS, AbstractCmd cmdToRun)
  {
    this.delayMS = delayMS;
    this.cmdToRun = cmdToRun;
  }

  public RunLaterCmd(long delayMS, AbstractCmd cmdToRun, boolean restore)
  {
    this.delayMS = delayMS;
    this.cmdToRun = cmdToRun;
    this.restore = restore;
  }

  @Override
  public void execute()
  {
    startedAtTime = System.currentTimeMillis();
    Scheduler.instance().schedule(this);
  }

  public long getStartedAtTime()
  {
    return startedAtTime;
  }

  public long getDelayMS()
  {
    return delayMS;
  }

  public AbstractCmd getCmdToRun()
  {
    return cmdToRun;
  }

  public boolean isRestore()
  {
    return restore;
  }

  @Override
  public String toString()
  {
    return "RunLaterCmd{" +
            "delayMS=" + delayMS +
            ", cmdToRun=" + cmdToRun +
            '}';
  }
}
