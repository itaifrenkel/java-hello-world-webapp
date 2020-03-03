package com.github.dagwud.woodlands.game.commands.locations.blacksmith;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.locations.CraftPromptCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.List;

public class CraftWeaponPromptCmd extends CraftPromptCmd<Weapon, Weapon>
{
  private static final long serialVersionUID = 1L;

  public CraftWeaponPromptCmd(PlayerCharacter character, PlayerState playerState)
  {
    super(character, playerState);
  }

  @Override
  public String produceFirstItemPromptMessage()
  {
    return "\"What've you got for me to work with?\"";
  }

  @Override
  protected String[] produceFirstItemOptions()
  {
    return produceWeaponOptions();
  }

  @Override
  protected String produceAcceptItemMessage()
  {
    return "\"Yeah, I can work with that. What else you got?\"";
  }

  @Override
  protected String produceSecondItemPromptMessage()
  {
    return "\"What else you got?\"";
  }

  @Override
  protected String produceAcceptedJobMessage()
  {
    return "\"I'll see what I can do. Come back in a few hours.\"";
  }

  @Override
  protected AbstractCmd createCraftCmd(Weapon firstItem, Weapon secondItem)
  {
    return new StartWeaponCraftCmd(getCharacter(), firstItem, secondItem);
  }

  @Override
  protected String produceCantWorkWithMessage()
  {
    return "I can't work with that. What've you got that's worth something?";
  }

  @Override
  protected Weapon findFirstItem(String name)
  {
    return (Weapon)findItem(name);
  }

  @Override
  protected Weapon findSecondItem(String name)
  {
    return (Weapon)findItem(name);
  }

  private String[] produceWeaponOptions()
  {
    return produceWeapons().toArray(new String[0]);
  }

  @Override
  protected String[] produceSecondItemOptions(String exclude)
  {
    List<String> weapons = produceWeapons();
    weapons.remove(exclude);
    return weapons.toArray(new String[0]);
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
    return weapons;
  }

}
