package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.gson.game.Weapon;

public class DamageInflicted
{
  private final Weapon inflictedWith;
  private final int baseDamage;
  private final int bonusDamage;

  public DamageInflicted(Weapon inflictedWith, int baseDamage, int bonusDamage)
  {
    this.inflictedWith = inflictedWith;
    this.baseDamage = baseDamage;
    this.bonusDamage = bonusDamage;
  }

  public Weapon getInflictedWith()
  {
    return inflictedWith;
  }

  public int getBaseDamage()
  {
    return baseDamage;
  }

  public int getBonusDamage()
  {
    return bonusDamage;
  }
}
