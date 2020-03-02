package com.github.dagwud.woodlands.game.commands.locations.blacksmith;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.domain.Blacksmith;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Damage;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class StartWeaponCraft extends AbstractCmd
{
  private final PlayerCharacter craftFor;
  private final Weapon firstWeapon;
  private final Weapon secondWeapon;

  public StartWeaponCraft(PlayerCharacter craftFor, Weapon firstWeapon, Weapon secondWeapon)
  {
    this.craftFor = craftFor;
    this.firstWeapon = firstWeapon;
    this.secondWeapon = secondWeapon;
  }

  @Override
  public void execute()
  {
    craftFor.getParty().getBlacksmith().setBusyCrafting(true);
    Weapon crafted = createCraftedWeapon();
    CommandDelegate.execute(new RunLaterCmd(Settings.BLACKSMITH_CRAFTING_TIME_MS, new FinishCraftingCmd(crafted, craftFor)));
    CommandDelegate.execute(new SendAdminMessageCmd("Blacksmith is crafting " + firstWeapon.getName() + " and " + secondWeapon.getName() + " into a " + crafted.getName() + " for " + craftFor.getName())); 
  }

  private Weapon createCraftedWeapon()
  {
    Weapon crafted = new Weapon(determineName(firstWeapon, secondWeapon));
    crafted.ranged = determineRanged(firstWeapon, secondWeapon);
    crafted.damage = determinedDamage(firstWeapon, secondWeapon);
    return crafted;
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
    damage.diceCount = average(firstWeapon.damage.diceCount, secondWeapon.damage.diceCount);
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

  private int average(int... values)
  {
    int total = 0;
    for (int value : values)
    {
      total += value;
    }
    return Math.floorDiv(total, values.length);
  }
}
