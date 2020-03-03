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
  private final Weapon toEnchant;
  private final ConsumableTrinket enchantWith;

  StartEnchantItemCmd(PlayerCharacter character, Weapon toEnchant, ConsumableTrinket enchantWith)
  {
    this.enchantFor = character;
    this.toEnchant = toEnchant;
    this.enchantWith = enchantWith;
  }

  @Override
  public void execute()
  {
    Alchemist alchemist = enchantFor.getParty().getAlchemist();
    alchemist.setBusyCrafting(true);
    Weapon crafted = createEnchantedWeapon();
    CommandDelegate.execute(new RunLaterCmd(Settings.ALCHEMIST_CRAFTING_TIME_MS, new FinishCraftingCmd(crafted, enchantFor, alchemist)));
    CommandDelegate.execute(new SendAdminMessageCmd("Alchemist is enchanting " + toEnchant.getName() + " and " + enchantWith.getName() + " into a " + crafted.getName() + " for " + enchantFor.getName()));
  }

  private Weapon createEnchantedWeapon()
  {
    Weapon weapon = new Weapon(toEnchant);
    weapon.damage.diceCount = weapon.damage.diceCount + 1;
    weapon.enchanted = true;
    return weapon;
  }
}
