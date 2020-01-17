package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;

public class CommandException extends WoodlandsRuntimeException
{
  public CommandException(Exception cause)
  {
    super(cause);
  }
}
