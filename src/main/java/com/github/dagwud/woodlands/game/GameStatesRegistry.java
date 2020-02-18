package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.RetrieveWorldCmd;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameStatesRegistry implements Serializable
{
  private static final long serialVersionUID = 1L;

  private transient static GameStatesRegistry instance;
  private final Map<Integer, PlayerState> gameStatesByCharacter = new HashMap<>();
  private PartyRegistry partyRegistry;
  private Scheduler scheduler;

  private GameStatesRegistry()
  {
    partyRegistry = new PartyRegistry();
  }

  public static PlayerState lookup(int chatId)
  {
    GameStatesRegistry registry = instance();
    if (!registry.gameStatesByCharacter.containsKey(chatId))
    {
      CreateGameStateCmd cmd = new CreateGameStateCmd(chatId);
      CommandDelegate.execute(cmd);

      PlayerState playerStateForChat = cmd.getCreatedPlayerState();
      if (registry.gameStatesByCharacter.containsKey(chatId))
      {
        throw new WoodlandsRuntimeException("Duplicate players for chat " + chatId);
      }
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
    instance = new GameStatesRegistry();
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
    RetrieveWorldCmd cmd = new RetrieveWorldCmd();
    CommandDelegate.execute(cmd);
    if (!cmd.retrieved() && null == instance)
    {
      instance = new GameStatesRegistry();
    }
  }

  public static void reload(GameStatesRegistry gameState)
  {
    instance = gameState;
  }

  PartyRegistry getPartyRegistry()
  {
    return partyRegistry;
  }

  Scheduler getScheduler()
  {
    if (null == scheduler)
    {
      scheduler = new Scheduler();
    }
    return scheduler;
  }
}
