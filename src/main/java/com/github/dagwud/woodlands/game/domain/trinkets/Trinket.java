package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Item;

public abstract class Trinket extends Item
{
  private static final long serialVersionUID = 1L;
  static final String RING_ICON = "\uD83D\uDC8D";

  public String name;
  public String icon = "";

  @Override
  public String summary(Fighter carrier, boolean includeName)
  {
    return getIcon() + (includeName ? getName() : "");
  }

  public abstract void equip(Fighter fighter);

  public abstract void unequip(Fighter fighter);
}
