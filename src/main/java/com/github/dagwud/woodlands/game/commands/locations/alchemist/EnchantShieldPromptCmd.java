package com.github.dagwud.woodlands.game.commands.locations.alchemist;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.locations.CraftPromptCmd;
import com.github.dagwud.woodlands.game.domain.Crafter;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.consumable.ConsumableTrinket;
import com.github.dagwud.woodlands.gson.game.Shield;

import java.util.ArrayList;
import java.util.List;

public class EnchantShieldPromptCmd extends CraftPromptCmd<Shield, ConsumableTrinket, Shield>
{
  public EnchantShieldPromptCmd(PlayerCharacter character, PlayerState playerState)
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
    return produceShields().toArray(new String[0]);
  }

  @Override
  protected Shield findFirstItem(String name)
  {
    return (Shield)findItem(name);
  }

  @Override
  protected String produceAcceptItemMessage()
  {
    return "\"Very nice. But everything is better with a little magic\"";
  }

  @Override
  protected String produceJobDeclinedMessage()
  {
    // No restrictions on shields, so this shouldn't happen...
    return "\"Uhhhhhhhh... this is awkward. This shouldn't have happened. Something must be very wrong with the world\"";
  }

  @Override
  protected Shield createCraftedItem(Shield firstItem, ConsumableTrinket secondItem)
  {
    Shield shield = new Shield();
    shield.name = firstItem.name;
    shield.strength = firstItem.strength + 1;
    shield.enchanted = true;
    return shield;
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
    return "\"Very well! Now if you'll excuse me, I'll get right to it. It won't take long - half an hourglass, at most\"";
  }

  @Override
  protected AbstractCmd createCraftCmd(Shield crafted)
  {
    return new StartEnchantShieldCmd(getCharacter(), crafted);
  }

  private List<String> produceShields()
  {
    List<String> shields = new ArrayList<>();
    if (getCharacter().getCarrying().getCarriedLeft() != null && getCharacter().getCarrying().getCarriedLeft() instanceof Shield)
    {
      shields.add(getCharacter().getCarrying().getCarriedLeft().getName());
    }
    if (getCharacter().getCarrying().getCarriedRight() != null && getCharacter().getCarrying().getCarriedRight() instanceof Shield)
    {
      shields.add(getCharacter().getCarrying().getCarriedRight().getName());
    }
    for (Item inactive : getCharacter().getCarrying().getCarriedInactive())
    {
      if (inactive instanceof Shield)
      {
        shields.add(inactive.getName());
      }
    }
    shields.add("Cancel");
    return shields;
  }

}
