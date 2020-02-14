package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

public class LesserHealingPotion extends HealingPotion
{
  private static final long serialVersionUID = -2698141417359675356L;
  private static final int HIT_POINTS_TO_RECOVER = 10;

  public LesserHealingPotion()
  {
    super("Potion of Lesser Healing", HIT_POINTS_TO_RECOVER);
  }
}
