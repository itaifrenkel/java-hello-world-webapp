package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.List;

public class RetrieveItemsCmd extends AbstractCmd
{
  private final PlayerCharacter character;

  public RetrieveItemsCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    int maxAllowedItems = character.determineMaxAllowedItems();
    if (character.getCarrying().countTotalCarried() >= maxAllowedItems)
    {
      CommandDelegate.execute(new SendMessageCmd(character.getPlayedBy().getChatId(), "You can't carry any more"));
      return;
    }

    Weapon chosenWeapon = chooseWeapon();

    character.getCarrying().getCarriedInactive().add(chosenWeapon);
    CommandDelegate.execute(new SendMessageCmd(character.getPlayedBy().getChatId(), "You pick up a "+ chosenWeapon.name + " " + chosenWeapon.getIcon() + determineDamageText(chosenWeapon)));
  }

  private Weapon chooseWeapon()
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
    return chosenWeapon;
  }

  private String determineDamageText(Weapon carrying)
  {
    int bonusDamage = character.getStats().getWeaponBonusDamage(carrying);

    String damageText = carrying.damage.determineAverageRoll();
    if (bonusDamage != 0)
    {
      damageText += " +" + bonusDamage;
    }
    return damageText;
  }
}
