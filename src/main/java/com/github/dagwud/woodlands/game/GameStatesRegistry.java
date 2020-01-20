package com.github.dagwud.woodlands.game;

import java.util.HashMap;
import java.util.Map;

public class GameStatesRegistry
{
  private static GameStatesRegistry instance;
  private final Map<Integer, PlayerState> gameStatesByCharacter = new HashMap<>();

  private GameStatesRegistry()
  {
  }

  public static PlayerState lookup(int chatId)
  {
    GameStatesRegistry registry = instance();
    if (!registry.gameStatesByCharacter.containsKey(chatId))
    {
      CreateGameStateCmd cmd = new CreateGameStateCmd(chatId);
      CommandDelegate.execute(cmd);

      PlayerState playerStateForChat = cmd.getCreatedPlayerState();
      registry.gameStatesByCharacter.put(chatId, playerStateForChat);
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
