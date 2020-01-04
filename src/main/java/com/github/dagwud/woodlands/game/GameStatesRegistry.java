package com.github.dagwud.woodlands.game;

import java.util.HashMap;
import java.util.Map;

public class GameStatesRegistry
{
  private static GameStatesRegistry instance;
  private Map<Integer, GameState> gameStatesByCharacter = new HashMap<>();

  private GameStatesRegistry()
  {
  }

  public static GameState lookup(int characterId)
  {
    GameStatesRegistry registry = instance();
    if (!registry.gameStatesByCharacter.containsKey(characterId))
    {
      registry.gameStatesByCharacter.put(characterId, new GameState());
    }
    return registry.gameStatesByCharacter.get(characterId);
  }

  private static GameStatesRegistry instance()
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
    instance = new GameStatesRegistry();
  }
}
