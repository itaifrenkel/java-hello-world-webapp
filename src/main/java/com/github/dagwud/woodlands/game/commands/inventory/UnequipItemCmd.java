package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;

public class UnequipItemCmd extends AbstractCmd
{
  private final PlayerCharacter character;
  private final Item toUnequip;

  public UnequipItemCmd(PlayerCharacter character, Item toUnequip)
  {
    this.character = character;
    this.toUnequip = toUnequip;
  }

  @Override
  public void execute()
  {
    if (character.getCarrying().isWorn(item))
    {
      if (toUnequip instanceof Trinket)
      {
        Trinket trinket = (Trinket) toUnequip;
        trinket.unequip(character);
      }
    }
  }
}
