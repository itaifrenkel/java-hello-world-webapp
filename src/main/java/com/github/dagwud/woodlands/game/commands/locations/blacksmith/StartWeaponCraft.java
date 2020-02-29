package com.github.dagwud.woodlands.game.commands.locations.blacksmith;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.Blacksmith;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Damage;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class StartWeaponCraft extends AbstractCmd
{
  private final Blacksmith blacksmith;
  private final PlayerCharacter craftFor;
  private final Weapon firstWeapon;
  private final Weapon secondWeapon;

  public StartWeaponCraft(Blacksmith blacksmith, PlayerCharacter craftFor, Weapon firstWeapon, Weapon secondWeapon)
  {
    this.blacksmith = blacksmith;
    this.craftFor = craftFor;
    this.firstWeapon = firstWeapon;
    this.secondWeapon = secondWeapon;
  }

  @Override
  public void execute()
  {
    blacksmith.setBusyCrafting(true);
    Weapon crafted = createCraftedWeapon();
    CommandDelegate.execute(new RunLaterCmd(Settings.BLACKSMITH_CRAFTING_TIME_MS, new FinishCraftingCmd(blacksmith, crafted, craftFor)));
  }

  private Weapon createCraftedWeapon()
  {
    Weapon crafted = new Weapon();
    crafted.ranged = determineRanged(firstWeapon, secondWeapon);
    crafted.damage = determinedDamage(firstWeapon, secondWeapon);
    crafted.name = determineName(firstWeapon, secondWeapon);
    return crafted;
  }

  private String determineName(Weapon firstWeapon, Weapon secondWeapon)
  {
    //todo nicer names
    return firstWeapon.name.substring(0, 3) + secondWeapon.name.substring(3);
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
