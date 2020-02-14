package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

public class GreaterManaPotion extends ManaPotion
{
  private static final long serialVersionUID = 1L;
  private static final int MANA_POINTS_TO_RECOVER = 3;

  public GreaterManaPotion()
  {
    super("Mana Potion", MANA_POINTS_TO_RECOVER);
  }
}
