package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Scheduler;
import com.github.dagwud.woodlands.game.log.Logger;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class RunScheduledCmd implements Callable<String>, Serializable
{
  private static final long serialVersionUID = 1L;

  private final AbstractCmd cmdToRun;
  private final long delayMS;
  private boolean restore;

  public RunScheduledCmd(long delayMS, AbstractCmd cmdToRun, boolean restore)
  {
    this.delayMS = delayMS;
    this.cmdToRun = cmdToRun;
    this.restore = restore;
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
      Logger.error("WARNING: Exception in asynchronous thread; can't be thrown to caller so logging it here:");
      try
      {
        SendAdminMessageCmd adminInfo = new SendAdminMessageCmd("Exception in asynchronous thread");
        CommandDelegate.execute(adminInfo);
        SendAdminMessageCmd exc = new SendAdminMessageCmd(e.toString());
        CommandDelegate.execute(exc);
      }
      catch (Exception ignore)
      {
        // oh well.
      }
      Logger.logError(e);
      throw e;
    }
    finally
    {
      if (restore)
      {
        Scheduler.instance().onComplete(cmdToRun);
      }
    }
  }
}
