package com.github.dagwud.woodlands.game;

import java.util.HashMap;
import java.util.Map;

public class GameStatesRegistry
{
  private static GameStatesRegistry instance;
  private final Map<Integer, GameState> gameStatesByCharacter = new HashMap<>();

  private GameStatesRegistry()
  {
  }

  public static GameState lookup(int chatId)
  {
    GameStatesRegistry registry = instance();
    if (!registry.gameStatesByCharacter.containsKey(chatId))
    {
      GameState gameStateForChat = new GameState();
      gameStateForChat.getVariables().setValue("chatId", String.valueOf(chatId));
      registry.gameStatesByCharacter.put(chatId, gameStateForChat);
    }
    return registry.gameStatesByCharacter.get(chatId);
  }

  public static void reset()
  {
    instance = null;
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
