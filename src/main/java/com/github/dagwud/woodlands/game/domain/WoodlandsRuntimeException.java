package com.github.dagwud.woodlands.game.domain;

public class WoodlandsRuntimeException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public WoodlandsRuntimeException(String message)
  {
    super(message);
  }

  public WoodlandsRuntimeException(Exception cause)
  {
    super(cause);
  }

  public WoodlandsRuntimeException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
