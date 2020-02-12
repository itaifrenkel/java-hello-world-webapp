package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

public class LesserHealingPotion extends HealingPotion
{
  private static final long SerialVersionUID = 1L;
  private static final int HIT_POINTS_TO_RECOVER = 10;

  public LesserHealingPotion()
  {
    super("Potion of Lesser Healing", HIT_POINTS_TO_RECOVER);
  }
}
