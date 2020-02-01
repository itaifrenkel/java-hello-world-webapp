package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.log.Logger;

public class ShutdownCmd extends AbstractCmd
{
  @Override
  public void execute()
  {
    try
    {
      Logger.info("Shutdown hook invoked; retreating characters to safe space...");
      CommandDelegate.execute(new ShutdownWarningCmd());
      Logger.info("Pesisting world...");
      PersistWorldCmd cmd = new PersistWorldCmd();
      CommandDelegate.execute(cmd);
      Logger.info("Ready for safe shutdown.");
    }
    catch (Exception e)
    {
      Logger.error("Error in asynchronous (shutdown) thread. Logging it here to avoid losing it");
      Logger.logError(e);
    }
  }
}
