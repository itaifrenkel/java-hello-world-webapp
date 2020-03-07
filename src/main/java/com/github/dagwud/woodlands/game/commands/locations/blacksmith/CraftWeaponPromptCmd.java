package com.github.dagwud.woodlands.game.commands.locations.blacksmith;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.locations.CraftPromptCmd;
import com.github.dagwud.woodlands.game.domain.Crafter;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Damage;
import com.github.dagwud.woodlands.gson.game.Weapon;
import com.github.dagwud.woodlands.game.Settings;

import java.util.ArrayList;
import java.util.List;

public class CraftWeaponPromptCmd extends CraftPromptCmd<Weapon, Weapon, Weapon>
{
  private static final long serialVersionUID = 1L;

  @Override
  protected Crafter getCrafter()
  {
    return getCharacter().getParty().getBlacksmith();
  }

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
    return "\"I'll see what I can do. Come back a bit later.\"";
  }

  @Override
  protected String produceJobDeclinedMessage()
  {
    return "\"Look, I don’t know who you were in a past life, but you clearly shouldn’t be hauling around that kind " +
            "of firepower. When you’re able to control that much damage, come back and chat to me, but for now " +
            "I can’t help you.";
  }

  @Override
  protected AbstractCmd createCraftCmd(Weapon crafted)
  {
    return new StartWeaponCraftCmd(getCharacter(), crafted);
  }

  @Override
  protected Weapon createCraftedItem(Weapon firstItem, Weapon secondItem)
  {
    Weapon crafted = new Weapon(determineName(firstItem, secondItem));
    crafted.ranged = determineRanged(firstItem, secondItem);
    crafted.damage = determinedDamage(firstItem, secondItem);
    crafted.enchanted = determineEnchanted(firstItem, secondItem);
    if (getCharacter().canHandleWeapon(crafted))
    {
      return crafted;
    }
    return null;
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

  private boolean determineEnchanted(Weapon firstWeapon, Weapon secondWeapon)
  {
    return firstWeapon.enchanted && secondWeapon.enchanted;
  }

  private String determineName(Weapon firstWeapon, Weapon secondWeapon)
  {
    StringBuilder m = new StringBuilder();
    String[] syllablesFirst = firstWeapon.getSyllables();
    int firstSegments = Math.min(1, syllablesFirst.length / 2);
    for (int i = 0; i < firstSegments; i++)
    {
      if (i > 0)
      {
        m.append("|");
      }
      m.append(syllablesFirst[i]);
    }

    m.append("|");

    String[] syllablesSecond = secondWeapon.getSyllables();
    int secondSegments = Math.min(1, syllablesSecond.length / 2);
    for (int i = secondSegments; i < syllablesSecond.length; i++)
    {
      if (i > secondSegments)
      {
        m.append("|");
      }
      m.append(syllablesSecond[i]);
    }
    return m.toString();
  }

  private boolean determineRanged(Weapon firstWeapon, Weapon secondWeapon)
  {
    if (firstWeapon.ranged == secondWeapon.ranged)
    {
      return firstWeapon.ranged;
    }
    return true;
  }

  private Damage determinedDamage(Weapon firstWeapon, Weapon secondWeapon)
  {
    Damage damage = new Damage();
    damage.diceCount = Math.max(firstWeapon.damage.diceCount, secondWeapon.damage.diceCount);
    damage.diceFaces = determineDiceFaces(firstWeapon.damage.diceFaces, secondWeapon.damage.diceFaces);
    return damage;
  }

  private int determineDiceFaces(int d1, int d2)
  {
    return nextDiceSize(Math.max(d1, d2));
  }

  private int nextDiceSize(int d1)
  {
    if (d1 == 8) return 12;
    if (d1 == 12) return 20;
    if (d1 == 20) return 24;
    return d1 + 2;
  }

}
