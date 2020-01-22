package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.ArrayList;
import java.util.List;

public class CarriedItems
{
  private Weapon carriedLeft;
  private Weapon carriedRight;
  private List<Weapon> carriedInactive = new ArrayList<>();

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

  public List<Weapon> getCarriedInactive()
  {
    return carriedInactive;
  }

}
