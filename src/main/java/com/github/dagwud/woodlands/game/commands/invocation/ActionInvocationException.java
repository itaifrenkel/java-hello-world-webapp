package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.WoodlandsException;

public class ActionInvocationException extends WoodlandsException
{
  public ActionInvocationException(String message)
  {
    super(message);
  }

  public ActionInvocationException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public ActionInvocationException(Throwable cause)
  {
    super(cause);
  }
}
