package com.github.dagwud.woodlands.game.items;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.inventory.UnequipItemCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Item;

public abstract class EquippableItem extends Item
{
  private static final long serialVersionUID = 1L;

  public void doEquip(Fighter character)
  {
    makeSpace(character);
    if (character.getCarrying().getCarriedLeft() == null)
    {
      character.getCarrying().setCarriedLeft(this);
    }
    else
    {
      character.getCarrying().setCarriedRight(this);
    }
  }

  private void makeSpace(Fighter carrier)
  {
    if (carrier.getCarrying().getCarriedRight() == null)
    {
      return;
    }
    if (carrier.getCarrying().getCarriedLeft() == null)
    {
      return;
    }
    Item moveL = carrier.getCarrying().getCarriedLeft();
    Item moveR = carrier.getCarrying().getCarriedRight();
    carrier.getCarrying().getCarriedInactive().add(moveR);
    carrier.getCarrying().setCarriedRight(moveL);
    carrier.getCarrying().setCarriedLeft(null);
    CommandDelegate.execute(new UnequipItemCmd(carrier, moveR));
  }
}
