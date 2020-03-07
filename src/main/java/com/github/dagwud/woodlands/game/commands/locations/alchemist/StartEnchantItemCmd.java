package com.github.dagwud.woodlands.game.commands.locations.alchemist;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.blacksmith.FinishCraftingCmd;
import com.github.dagwud.woodlands.game.domain.Alchemist;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.consumable.ConsumableTrinket;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class StartEnchantItemCmd extends AbstractCmd
{
  private final PlayerCharacter enchantFor;
  private final Weapon enchantedWeapon;

  StartEnchantItemCmd(PlayerCharacter character, Weapon enchantedWeapon)
  {
    this.enchantFor = character;
    this.enchantedWeapon = enchantedWeapon;
  }

  @Override
  public void execute()
  {
    Alchemist alchemist = enchantFor.getParty().getAlchemist();
    alchemist.setBusyCrafting(enchantFor, enchantedWeapon);
    alchemist.setCraftingExpectedEndTime(Settings.ALCHEMIST_ENCHANT_WEAPON_TIME_MS);
    CommandDelegate.execute(new RunLaterCmd(Settings.ALCHEMIST_ENCHANT_WEAPON_TIME_MS, new FinishCraftingCmd<>(enchantedWeapon, enchantFor, alchemist)));
    CommandDelegate.execute(new SendAdminMessageCmd("Alchemist is enchanting a " + enchantedWeapon.getName() + " for " + enchantFor.getName()));
  }

}
