package com.github.dagwud.woodlands.game.commands.locations.blacksmith;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.inventory.DoGiveItemCmd;
import com.github.dagwud.woodlands.game.domain.Crafter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class FinishCraftingCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Weapon crafted;
  private final PlayerCharacter craftedFor;
  private final Crafter<Weapon> crafter;

  public FinishCraftingCmd(Weapon crafted, PlayerCharacter craftedFor, Crafter crafter)
  {
    this.crafted = crafted;
    this.craftedFor = craftedFor;
    this.crafter = crafter;
  }

  @Override
  public void execute()
  {
    CommandDelegate.execute(new DoGiveItemCmd(null, crafter, crafted));
    crafter.completeCrafting(craftedFor);
    CommandDelegate.execute(new SendAdminMessageCmd("Crafting complete: " + crafter.getName() + " has completed " + crafted.getName() + " for " + craftedFor.getName()));
  }

  @Override
  public String toString()
  {
    return "FinishCraftingCmd[crafter=\"" + crafter.getName() + "\",for=\"" + craftedFor.getName() + "\"]";
}
