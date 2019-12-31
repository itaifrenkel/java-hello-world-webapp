package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;

public class UnknownActionException extends ActionInvocationException
{
  public UnknownActionException(String message)
  {
    super(message);
  }
}
