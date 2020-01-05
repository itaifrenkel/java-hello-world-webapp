package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;

abstract class ActionParameterException extends ActionInvocationException
{
  ActionParameterException(String message)
  {
    super(message);
  }
}
