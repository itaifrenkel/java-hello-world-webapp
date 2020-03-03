package com.github.dagwud.woodlands.game.commands.locations.alchemist;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.locations.CraftPromptCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.consumable.ConsumableTrinket;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.List;

public class EnchantItemPromptCmd extends CraftPromptCmd<Weapon, ConsumableTrinket>
{
  public EnchantItemPromptCmd(PlayerCharacter character, PlayerState playerState)
  {
    super(character, playerState);
  }

  @Override
  protected String produceFirstItemPromptMessage()
  {
    return "\"What do you have that needs enchanting?\"";
  }

  @Override
  protected String[] produceFirstItemOptions()
  {
    return produceWeapons().toArray(new String[0]);
  }

  @Override
  protected Weapon findFirstItem(String name)
  {
    return (Weapon)findItem(name);
  }

  @Override
  protected String produceAcceptItemMessage()
  {
    return "\"Oh, a fine specimen. But certainly we can give it a little something extra\"";
  }

  @Override
  protected String produceCantWorkWithMessage()
  {
    return "\"I don't know what you expect me to do with that.\"";
  }

  @Override
  protected String produceSecondItemPromptMessage()
  {
    return "\"And from what shall I draw the magic?\"";
  }

  @Override
  protected String[] produceSecondItemOptions(String exclude)
  {
    return producePotions().toArray(new String[0]);
  }

  @Override
  protected ConsumableTrinket findSecondItem(String name)
  {
    return (ConsumableTrinket)findItem(name);
  }

  @Override
  protected String produceAcceptedJobMessage()
  {
    return "\"Very well! Now if you'll excuse me, I'll get right to it. Come back when the sun overhead has travelled nine hundred minutes of an arc\"";
  }

  @Override
  protected AbstractCmd createCraftCmd(Weapon firstItem, ConsumableTrinket secondItem)
  {
    return new StartEnchantItemCmd(getCharacter(), firstItem, secondItem);
  }

  private List<String> produceWeapons()
  {
    List<String> weapons = new ArrayList<>();
    if (getCharacter().getCarrying().getCarriedLeft() != null && getCharacter().getCarrying().getCarriedLeft() instanceof Weapon)
    {
      weapons.add(getCharacter().getCarrying().getCarriedLeft().getName());
    }
    if (getCharacter().getCarrying().getCarriedRight() != null && getCharacter().getCarrying().getCarriedRight() instanceof Weapon)
    {
      weapons.add(getCharacter().getCarrying().getCarriedRight().getName());
    }
    for (Item inactive : getCharacter().getCarrying().getCarriedInactive())
    {
      if (inactive instanceof Weapon)
      {
        weapons.add(inactive.getName());
      }
    }
    weapons.add("Cancel");
    return weapons;
  }

  private List<String> producePotions()
  {
    List<String> potions = new ArrayList<>();
    if (getCharacter().getCarrying().getCarriedLeft() != null && getCharacter().getCarrying().getCarriedLeft() instanceof ConsumableTrinket)
    {
      potions.add(getCharacter().getCarrying().getCarriedLeft().getName());
    }
    if (getCharacter().getCarrying().getCarriedRight() != null && getCharacter().getCarrying().getCarriedRight() instanceof ConsumableTrinket)
    {
      potions.add(getCharacter().getCarrying().getCarriedRight().getName());
    }
    for (Item inactive : getCharacter().getCarrying().getCarriedInactive())
    {
      if (inactive instanceof ConsumableTrinket)
      {
        potions.add(inactive.getName());
      }
    }
    potions.add("Cancel");
    return potions;
  }
}
