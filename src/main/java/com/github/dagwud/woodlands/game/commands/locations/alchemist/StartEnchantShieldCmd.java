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
import com.github.dagwud.woodlands.gson.game.Shield;

public class StartEnchantShieldCmd extends AbstractCmd
{
  private final PlayerCharacter enchantFor;
  private final Shield enchantedShield;

  StartEnchantShieldCmd(PlayerCharacter character, Shield enchantedShield)
  {
    this.enchantFor = character;
    this.enchantedShield = enchantedShield;
  }

  @Override
  public void execute()
  {
    Alchemist alchemist = enchantFor.getParty().getAlchemist();
    alchemist.setBusyCrafting(enchantFor, enchantedShield);
    alchemist.setCraftingExpectedEndTime(Settings.ALCHEMIST_ENCHANT_SHIELD_TIME_MS);
    CommandDelegate.execute(new RunLaterCmd(Settings.ALCHEMIST_ENCHANT_SHIELD_TIME_MS, new FinishCraftingCmd<>(enchantedShield, enchantFor, alchemist)));
    CommandDelegate.execute(new SendAdminMessageCmd("Alchemist is enchanting  a " + enchantedShield.getName() + " for " + enchantFor.getName()));
  }

}
