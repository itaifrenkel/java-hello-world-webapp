package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.characterclasses.CharacterClassesCacheFactory;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.instructions.AbstractCmd;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.CharacterClass;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.Map;

public class CreateGameStateCmd extends AbstractCmd
{
  private final int chatId;
  private GameState createdGameState;

  CreateGameStateCmd(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    createdGameState = new GameState();
    createdGameState.setPlayer(new Player(chatId));
    populateCharacterClasses(createdGameState);
    populateItems(createdGameState);
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

  GameState getCreatedGameState()
  {
    return createdGameState;
  }
}
