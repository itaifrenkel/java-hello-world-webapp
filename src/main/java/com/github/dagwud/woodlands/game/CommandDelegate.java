package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.CommandException;

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
