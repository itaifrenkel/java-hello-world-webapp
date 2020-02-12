package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

public class LesserManaPotion extends ManaPotion
{
  private static final long SerialVersionUID = 1L;
  private static final int MANA_POINTS_TO_RECOVER = 1;

  public LesserManaPotion()
  {
    super("Lesser Mana Potion", MANA_POINTS_TO_RECOVER);
  }
}
