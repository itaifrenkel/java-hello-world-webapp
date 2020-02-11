package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

public class MassiveHealingPotion extends HealingPotion
{
  private static final int HIT_POINTS_TO_RECOVER = 50;

  public MassiveHealingPotion()
  {
    super("Potion of Massive Healing", HIT_POINTS_TO_RECOVER);
  }
}
