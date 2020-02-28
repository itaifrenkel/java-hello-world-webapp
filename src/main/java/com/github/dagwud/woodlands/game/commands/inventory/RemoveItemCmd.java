package com.github.dagwud.woodlands.game.commands.inventory;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.CarriedItems;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.items.UnknownWeaponException;

public class RemoveItemCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;
  private final GameCharacter from;
  private final Item item;

  public RemoveItemCmd(GameCharacter from, Item item)
  {
    this.from = from;
    this.item = item;
  }

  @Override
  public void execute()
  {
    CarriedItems carrying = from.getCarrying();
    if (carrying.getCarriedLeft() == item)
    {
      carrying.setCarriedLeft(null);
      return;
    }
    if (carrying.getCarriedRight() == item)
    {
      carrying.setCarriedRight(null);
      return;
    }
    boolean removed = carrying.getCarriedInactive().remove(item);
    if (removed)
    {
      return;
    }

    throw new UnknownWeaponException(from.getName() + " is not carrying " + item.getName());
  }
}
