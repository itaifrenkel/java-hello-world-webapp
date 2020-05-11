package com.github.dagwud.woodlands.game.commands.locations.blacksmith;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.domain.npc.Blacksmith;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
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
    double maxDamage = Settings.MAX_CRAFTABLE_WEAPON_DAMAGE;
    double scaledDamage = Math.min(maxDamage, damage * 2.0d);
    long maxTime = Settings.BLACKSMITH_CRAFTING_TIME_MS;
    double perc = scaledDamage / maxDamage;
    return (long) (perc * maxTime);
  }
}
