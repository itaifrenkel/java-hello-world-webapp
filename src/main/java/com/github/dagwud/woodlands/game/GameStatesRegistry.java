package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.RetrieveWorldCmd;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.location.tavern.JukeBox;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GameStatesRegistry implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static transient boolean limpMode = false;

  private transient static GameStatesRegistry instance;

  private final Map<Integer, PlayerState> gameStatesByCharacter = new HashMap<>();
  private PartyRegistry partyRegistry;
  private Scheduler scheduler;
  private JukeBox jukeBox;

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
    Collection<PlayerState> values = instance().gameStatesByCharacter.values();

    return values.stream()
            .filter(p -> p.getActiveCharacter() != null)
            .collect(Collectors.toList());
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

  public JukeBox getJukeBox()
  {
    if (jukeBox == null)
    {
      jukeBox = new JukeBox();
    }

    return jukeBox;
  }

  Scheduler getScheduler()
  {
    if (null == scheduler)
    {
      scheduler = new Scheduler();
    }
    return scheduler;
  }

  public static void setLimpMode(boolean limpMode)
  {
    GameStatesRegistry.limpMode = limpMode;
  }

  public static boolean isLimpMode()
  {
    return limpMode;
  }
}
