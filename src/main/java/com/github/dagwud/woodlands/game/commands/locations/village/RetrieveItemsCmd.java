package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.List;

public class RetrieveItemsCmd extends AbstractCmd
{
  private final GameCharacter character;

  public RetrieveItemsCmd(GameCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    Weapon chosenWeapon = null;
    while (chosenWeapon == null)
    {
      List<Weapon> allWeapons = ItemsCacheFactory.instance().getCache().getWeapons();
      int rand = (int) (Math.random() * allWeapons.size());
      chosenWeapon = allWeapons.get(rand);
      if (chosenWeapon.preventSpawning)
      {
        chosenWeapon = null; // try again
      }
    }

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
