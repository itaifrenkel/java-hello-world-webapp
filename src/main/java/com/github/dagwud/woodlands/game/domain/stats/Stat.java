package com.github.dagwud.woodlands.game.domain.stats;

public class Stat
{
  private int base;
  private int bonus;

  Stat(int base, int bonus)
  {
    this.base = base;
    this.bonus = bonus;
  }

  public int total()
  {
    return base + bonus;
  }

  @Override
  public String toString()
  {
    return base + (bonus == 0 ? "" : " + " + bonus);
  }
}
