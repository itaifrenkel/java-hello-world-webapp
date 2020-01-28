package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.PersistWorldCmd;
import com.github.dagwud.woodlands.game.commands.ShutdownWarningCmd;

public class ShutdownHookThread extends Thread
{
  @Override
  public void run()
  {
    try
    {
      System.out.println("Shutdown hook invoked; retreating characters to safe space...");
      CommandDelegate.execute(new ShutdownWarningCmd());
      System.out.println("Pesisting world...");
      PersistWorldCmd cmd = new PersistWorldCmd();
      CommandDelegate.execute(cmd);
      System.out.println("Ready for safe shutdown.");
    }
    catch (Exception e)
    {
      System.err.println("Error in asynchronous (shutdown) thread. Logging it here to avoid losing it");
      e.printStackTrace();
    }
  }
}
