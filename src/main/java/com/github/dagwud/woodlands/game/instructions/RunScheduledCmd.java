package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.CommandDelegate;

import java.util.concurrent.Callable;

public class RunScheduledCmd implements Callable<String>
{
  private final AbstractCmd cmdToRun;
  private long delayMS;

  RunScheduledCmd(long delayMS, AbstractCmd cmdToRun)
  {
    this.delayMS = delayMS;
    this.cmdToRun = cmdToRun;
  }

  @Override
  public String call() throws Exception
  {
    Thread.sleep(delayMS);
    CommandDelegate.execute(cmdToRun);
    return null;
  }
}
