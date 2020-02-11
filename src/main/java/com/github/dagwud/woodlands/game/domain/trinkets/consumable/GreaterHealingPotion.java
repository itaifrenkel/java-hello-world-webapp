package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

public class GreaterHealingPotion extends HealingPotion
{
  private static final int HIT_POINTS_TO_RECOVER = 20;

  public GreaterHealingPotion()
  {
    super("Potion of Greater Healing", HIT_POINTS_TO_RECOVER);
  }
}
