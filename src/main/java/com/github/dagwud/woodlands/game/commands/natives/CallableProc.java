package com.github.dagwud.woodlands.game.commands.natives;

import java.util.concurrent.Callable;

class CallableProc<T> implements Callable<String>
{
  private final long delayMS;
  private final String timerInfo;

  CallableProc(long delayMS, String timerInfo)
  {
    this.delayMS = delayMS;
    this.timerInfo = timerInfo;
  }

  @Override
  public String call() throws Exception
  {
    System.out.println("CALLED: " + timerInfo);
    System.out.println("WAITING: " + delayMS);
    Thread.sleep(delayMS);
    System.out.println("DONE WAITING!");
    return "";
  }
}
