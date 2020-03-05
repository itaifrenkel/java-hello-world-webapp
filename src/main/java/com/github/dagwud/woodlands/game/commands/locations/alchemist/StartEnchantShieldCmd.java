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
  private final Shield toEnchant;
  private final ConsumableTrinket enchantWith;

  StartEnchantShieldCmd(PlayerCharacter character, Shield toEnchant, ConsumableTrinket enchantWith)
  {
    this.enchantFor = character;
    this.toEnchant = toEnchant;
    this.enchantWith = enchantWith;
  }

  @Override
  public void execute()
  {
    Alchemist alchemist = enchantFor.getParty().getAlchemist();
    Shield crafted = createEnchantedShield();
    alchemist.setBusyCrafting(enchantFor, crafted);
    CommandDelegate.execute(new RunLaterCmd(Settings.ALCHEMIST_CRAFTING_TIME_MS, new FinishCraftingCmd<Shield>(crafted, enchantFor, alchemist)));
    CommandDelegate.execute(new SendAdminMessageCmd("Alchemist is enchanting " + toEnchant.getName() + " and " + enchantWith.getName() + " into a " + crafted.getName() + " for " + enchantFor.getName()));
  }

  private Shield createEnchantedShield()
  {
    Shield shield = new Shield();
    shield.name = toEnchant.name;
    shield.strength = toEnchant.strength + 1;
    shield.enchanted = true;
    return shield;
  }
}
