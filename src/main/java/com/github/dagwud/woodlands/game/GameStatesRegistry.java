package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.PersistWorldCmd;
import com.github.dagwud.woodlands.game.commands.RetrieveWorldCmd;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
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

  public static Collection<PlayerState> allPlayerStates()
  {
    return Collections.unmodifiableCollection(instance().gameStatesByCharacter.values());
  }

  public static void reset()
  {
    instance = null;
  }

  public static GameStatesRegistry instance()
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
    if (new File(PersistWorldCmd.GAME_STATE_FILE).exists())
    {
      CommandDelegate.execute(new RetrieveWorldCmd());
    }
    instance = new GameStatesRegistry();
  }

  public static void reload(GameStatesRegistry gameState)
  {
    instance = gameState;
  }
}
