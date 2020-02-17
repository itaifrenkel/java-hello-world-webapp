package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.trinkets.Trinket;

public class UnequipItemCmd extends AbstractCmd
{
  private final Fighter character;
  private final Item toUnequip;

  public UnequipItemCmd(Fighter character, Item toUnequip)
  {
    super(new AbleToActPrerequisite(characterToMove));
    this.character = character;
    this.toUnequip = toUnequip;
  }

  @Override
  public void execute()
  {
    if (character.getCarrying().isWorn(toUnequip))
    {
      if (toUnequip instanceof Trinket)
      {
        Trinket trinket = (Trinket) toUnequip;
        trinket.unequip(character);
      }
    }
  }
}
