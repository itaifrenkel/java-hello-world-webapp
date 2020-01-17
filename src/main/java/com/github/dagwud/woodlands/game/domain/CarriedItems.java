package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.gson.game.Weapon;

public class CarriedItems
{
  private Weapon carriedLeft;
  private Weapon carriedRight;

  public Weapon getCarriedLeft()
  {
    return carriedLeft;
  }

  public void setCarriedLeft(Weapon carriedLeft)
  {
    this.carriedLeft = carriedLeft;
  }

  public Weapon getCarriedRight()
  {
    return carriedRight;
  }

  public void setCarriedRight(Weapon carriedRight)
  {
    this.carriedRight = carriedRight;
  }
}
