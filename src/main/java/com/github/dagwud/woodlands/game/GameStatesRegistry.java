package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.characterclasses.CharacterClassesCacheFactory;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.CharacterClass;
import com.github.dagwud.woodlands.gson.game.Weapon;

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
      populateCharacterClasses(gameStateForChat);
      populateItems(gameStateForChat);
      registry.gameStatesByCharacter.put(chatId, gameStateForChat);
    }
    return registry.gameStatesByCharacter.get(chatId);
  }

  public static void reset()
  {
    instance = null;
  }

  private static void populateCharacterClasses(GameState gameState)
  {
    for (CharacterClass characterClass : CharacterClassesCacheFactory.instance().getCharacterClasses())
    {
      String varPrefix = "__Classes." + characterClass.name + ".";
      if (characterClass.stats != null)
      {
        for (Map.Entry<String, String> stat : characterClass.stats.entrySet())
        {
          gameState.getVariables().setValue(varPrefix + stat.getKey(), stat.getValue());
        }
      }
    }
  }

  private static void populateItems(GameState gameState)
  {
    for (Weapon weapon : ItemsCacheFactory.instance().getCache().getWeapons())
    {
      gameState.getVariables().setValue("Weapons." + weapon.name + ".damage",
        weapon.damage.determineAverageRoll());
      gameState.getVariables().setValue("Weapons." + weapon.name + ".icon",
        weapon.getIcon());
    }
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
