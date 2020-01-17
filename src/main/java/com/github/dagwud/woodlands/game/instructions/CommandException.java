package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;

public class CommandException extends WoodlandsRuntimeException
{
  public CommandException(Exception cause)
  {
    super(cause);
  }
}
