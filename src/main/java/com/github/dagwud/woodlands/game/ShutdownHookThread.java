package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.ShutdownWarningCmd;

public class ShutdownHookThread extends Thread
{
  @Override
  public void run()
  {
    try
    {
      CommandDelegate.execute(new ShutdownWarningCmd());
    }
    catch (Exception e)
    {
      System.err.println("Error in asynchronous (shutdown) thread. Logging it here to avoid losing it");
      e.printStackTrace();
    }
  }
}
