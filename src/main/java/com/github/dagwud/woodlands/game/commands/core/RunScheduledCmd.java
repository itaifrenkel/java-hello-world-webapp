package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;

import java.util.concurrent.Callable;

public class RunScheduledCmd implements Callable<String>
{
  private final AbstractCmd cmdToRun;
  private final long delayMS;

  RunScheduledCmd(long delayMS, AbstractCmd cmdToRun)
  {
    this.delayMS = delayMS;
    this.cmdToRun = cmdToRun;
  }

  @Override
  public String call() throws Exception
  {
    try
    {
      Thread.sleep(delayMS);
      CommandDelegate.execute(cmdToRun);
      return null;
    }
    catch (Exception e)
    {
      System.err.println("WARNING: Exception in asynchronous thread; can't be thrown to caller so logging it here:");
      e.printStackTrace();
      throw e;
    }
  }
}
