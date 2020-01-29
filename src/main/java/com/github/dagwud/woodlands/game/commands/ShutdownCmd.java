package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;

public class ShutdownCmd extends AbstractCmd
{
  @Override
  public void execute()
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
