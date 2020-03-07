package com.github.dagwud.woodlands.game.commands.locations.alchemist;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.locations.CraftPromptCmd;
import com.github.dagwud.woodlands.game.domain.Crafter;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.consumable.ConsumableTrinket;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.List;

public class EnchantItemPromptCmd extends CraftPromptCmd<Weapon, ConsumableTrinket, Weapon>
{
  public EnchantItemPromptCmd(PlayerCharacter character, PlayerState playerState)
  {
    super(character, playerState);
  }

  @Override
  public Crafter getCrafter()
  {
    return getCharacter().getParty().getAlchemist();
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
  protected Weapon createCraftedItem(Weapon firstItem, ConsumableTrinket secondItem)
  {
    Weapon crafted = new Weapon(firstItem);
    crafted.damage.diceCount = crafted.damage.diceCount + 1;
    crafted.enchanted = true;
    if (!getCharacter().canHandleWeapon(crafted))
    {
      return null;
    }
    return crafted;
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
  protected String produceJobDeclinedMessage()
  {
    // No restrictions on shields, so this shouldn't happen...
    return "\"Uhhhhhhhh... this is awkward. This shouldn't have happened. Something must be very wrong with the world\"";
  }

  @Override
  protected AbstractCmd createCraftCmd(Weapon craftedItem)
  {
    return new StartEnchantItemCmd(getCharacter(), craftedItem);
  }

}
