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

public class StartWeaponCraftCmd extends AbstractCmd
{
  private final PlayerCharacter craftFor;
  private final Weapon weaponToCraft;

  public StartWeaponCraftCmd(PlayerCharacter craftFor, Weapon weaponToCraft)
  {
    this.craftFor = craftFor;
    this.weaponToCraft = weaponToCraft;
  }

  @Override
  public void execute()
  {
    Blacksmith blacksmith = craftFor.getParty().getBlacksmith();
    long craftTimeMS = determineCraftTime(weaponToCraft);
    blacksmith.setBusyCrafting(craftFor, weaponToCraft);
    blacksmith.setCraftingExpectedEndTime(craftTimeMS);
    CommandDelegate.execute(new RunLaterCmd(craftTimeMS, new FinishCraftingCmd<Weapon>(weaponToCraft, craftFor, blacksmith)));
    CommandDelegate.execute(new SendAdminMessageCmd("Blacksmith is crafting " + weaponToCraft.getName() + " for " + craftFor.getName())); 
  }

  // for every 1% of the max damage, it takes 2% of max time to craft
  private long determineCraftTime(Weapon craft)
  {
    double damage = craft.damage.determineAverageRollAmount();
    double maxDamage = (double) Settings.MAX_CRAFTABLE_WEAPON_DAMAGE;
    double scaledDamage = Math.min(maxDamage, damage * 2.0d);
    long maxTime = Settings.BLACKSMITH_CRAFTING_TIME_MS;
    double perc = scaledDamage / maxDamage;
    return (long) (perc * maxTime);
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
