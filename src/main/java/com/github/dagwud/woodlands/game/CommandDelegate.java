package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.CommandException;

public class CommandDelegate
{
  public static void execute(AbstractCmd cmd)
  {
    try
    {
      cmd.execute();
    }
    catch (Exception e)
    {
      if (e instanceof CommandException)
      {
        throw (CommandException)e;
      }
      throw new CommandException(e);
    }
  }
}
