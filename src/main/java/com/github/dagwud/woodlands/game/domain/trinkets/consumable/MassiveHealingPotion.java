package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

public class MassiveHealingPotion extends HealingPotion
{
  private static final long serialVersionUID = -6414480749267825792L;
  private static final int HIT_POINTS_TO_RECOVER = 50;

  public MassiveHealingPotion()
  {
    super("Potion of Massive Healing", HIT_POINTS_TO_RECOVER);
  }
}
