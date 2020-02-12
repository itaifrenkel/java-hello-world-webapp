package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Item;

public abstract class WearableTrinket extends Trinket
{
  private static final long serialVersionUID = 1L;

  @Override
  public void doEquip(Fighter character)
  {
    character.getCarrying().getWorn().add(this);
    equip(character);
  }
}
