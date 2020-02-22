package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Scheduler;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.log.Logger;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class RunScheduledCmd implements Callable<String>, Serializable
{
  private static final long serialVersionUID = 1L;

  private final AbstractCmd cmdToRun;
  private final long delayMS;

  public RunScheduledCmd(long delayMS, AbstractCmd cmdToRun)
  {
    this(System.currentTimeMillis(), delayMS, cmdToRun);
  }

  public RunScheduledCmd(long startDelayAtTime, long delayMS, AbstractCmd cmdToRun)
  {
    long alreadyElapsedMS = System.currentTimeMillis() - startDelayAtTime;
    this.delayMS = Math.max(0, delayMS - alreadyElapsedMS);
    this.cmdToRun = cmdToRun;
System.out.println("Delay: " + delayMS + " -> " + this.delayMS);
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
        SendMessageCmd adminInfo = new SendMessageCmd(Settings.ADMIN_CHAT, "Exception in asynchronous thread");
        CommandDelegate.execute(adminInfo);
        SendMessageCmd exc = new SendMessageCmd(Settings.ADMIN_CHAT, e.toString());
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
      Scheduler.instance().onComplete(cmdToRun);
    }
  }
}
