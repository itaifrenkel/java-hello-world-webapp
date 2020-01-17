package com.github.dagwud.woodlands.game.commands.values;

public class WoodlandsRuntimeException extends RuntimeException
{
  public WoodlandsRuntimeException(Exception cause)
  {
    super(cause);
  }

  public WoodlandsRuntimeException(String message)
  {
    super(message);
  }
}
