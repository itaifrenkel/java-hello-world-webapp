package com.github.dagwud.woodlands.game.domain.stats;

public class Stat
{
  private int base;
  private int bonus;

  Stat()
  {
    this(0, 0);
  }

  Stat(int base, int bonus)
  {
    this.base = base;
    this.bonus = bonus;
  }

  public int total()
  {
    return base + bonus;
  }

  public void addBonus(int bonusAmount)
  {
    this.bonus += bonusAmount;
  }

  public void removeBonus(int bonusAmount)
  {
    this.bonus -= bonusAmount;
  }

  @Override
  public String toString()
  {
    return base + (bonus == 0 ? "" : " + " + bonus);
  }
}
