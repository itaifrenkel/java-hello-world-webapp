package com.github.dagwud.woodlands.game;

public class GameState
{
  private static GameState instance;

  GameState()
  {
  }

  public static GameState instance()
  {
    if (null == instance)
    {
      createInstance();
    }
    return instance;
  }

  private synchronized static void createInstance()
  {
    if (null != instance)
    {
      return;
    }
    instance = new GameState();
  }
}
