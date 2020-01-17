package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.List;

public class RetrieveItemsCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter character;

  RetrieveItemsCmd(int chatId, GameCharacter character)
  {
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    List<Weapon> allWeapons = ItemsCacheFactory.instance().getCache().getWeapons();
    int rand = (int) (Math.random() * allWeapons.size());
    Weapon chosenWeapon = allWeapons.get(rand);

    if (character.getCarrying().getCarriedLeft() == null)
    {
      character.getCarrying().setCarriedLeft(chosenWeapon);
    }
    else
    {
      character.getCarrying().setCarriedRight(chosenWeapon);
    }
  }
}
