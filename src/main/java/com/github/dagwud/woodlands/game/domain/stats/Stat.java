package com.github.dagwud.woodlands.game.domain.stats;

import java.io.Serializable;

public class Stat implements Serializable
{
  private static final long serialVersionUID = 1L;

  private int base;
  private int bonus;

  Stat()
  {
    this(0, 0);
  }

  public Stat(int base, int bonus)
  {
    this.base = base;
    this.bonus = bonus;
  }

  public int getBase()
  {
    return base;
  }

  public void setBase(int base)
  {
    this.base = base;
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

  public void clearBonuses()
  {
    this.bonus = 0;
  }

  @Override
  public String toString()
  {
    return base + (bonus == 0 ? "" : " + " + bonus);
  }
}
