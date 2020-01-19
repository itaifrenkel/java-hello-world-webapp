package com.github.dagwud.woodlands.game.domain;

public class WoodlandsRuntimeException extends RuntimeException
{
  public WoodlandsRuntimeException(Exception cause)
  {
    super(cause);
  }

  public WoodlandsRuntimeException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
