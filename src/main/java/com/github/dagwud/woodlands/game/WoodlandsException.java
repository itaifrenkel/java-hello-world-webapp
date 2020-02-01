package com.github.dagwud.woodlands.game;

public abstract class WoodlandsException extends Exception
{
  private static final long serialVersionUID = 1L;

  public WoodlandsException(String message)
  {
    super(message);
  }
}
