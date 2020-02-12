package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.domain.Fighter;

public class LootBag extends WearableTrinket
{
  private static final int CARRY_CAPACITY_BOOST = 3;
  private static final long serialVersionUID = 1L;

  @Override
  public String getName()
  {
    return "Loot Bag";
  }

  @Override
  public String getIcon()
  {
    return "";
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return "";
  }

  @Override
  public void equip(Fighter fighter)
  {
  }

  @Override
  public void unequip(Fighter fighter)
  {
  }

  public int getProvidedSpaces()
  {
    return CARRY_CAPACITY_BOOST;
  }
}
