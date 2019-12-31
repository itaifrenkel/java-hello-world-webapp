package com.github.dagwud.woodlands.game;

public abstract class WoodlandsException extends Exception
{
  public WoodlandsException(String message)
  {
    super(message);
  }

  public WoodlandsException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
