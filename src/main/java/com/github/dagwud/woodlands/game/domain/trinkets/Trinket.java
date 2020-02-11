package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Item;

public abstract class Trinket extends Item
{
  private static final long serialVersionUID = 1L;

  static final String BEADS_ICON = "\uD83D\uDCFF";
  static final String AMULET_ICON = "\uD83D\uDC8E";
  static final String WARD_ICON = "\uD83D\uDD2E";
  protected static final String POTION_ICON = "\uD83E\uDDEA";

  public String name;
  public String icon = "";

  @Override
  public String summary(Fighter carrier, boolean includeName)
  {
    return getIcon() + (includeName ? getName() : "");
  }

  public abstract void equip(Fighter fighter);

  public abstract void unequip(Fighter fighter);

  String produceUnequipMessage()
  {
    return "You no longer feel the effects of the " + getName();
  }
}
