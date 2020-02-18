package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.Scheduler;

public class RunLaterCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final long delayMS;
  private final AbstractCmd cmdToRun;

  public RunLaterCmd(long delayMS, AbstractCmd cmdToRun)
  {
    this.delayMS = delayMS;
    this.cmdToRun = cmdToRun;
  }

  @Override
  public void execute()
  {
    Scheduler.instance().schedule(this);
  }

  public long getDelayMS()
  {
    return delayMS;
  }

  public AbstractCmd getCmdToRun()
  {
    return cmdToRun;
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
