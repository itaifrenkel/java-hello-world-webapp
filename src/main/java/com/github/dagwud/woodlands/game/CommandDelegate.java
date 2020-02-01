package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.CommandException;
import com.github.dagwud.woodlands.game.log.Logger;

public class CommandDelegate
{
  public static void execute(AbstractCmd cmd)
  {
    try
    {
      if (cmd.verifyPrerequisites())
      {
        cmd.execute();
        Logger.log(cmd.toString());
      }
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
