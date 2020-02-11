package com.github.dagwud.woodlands.game.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarriedItems implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Item carriedLeft;
  private Item carriedRight;
  private List<Item> worn;
  private List<Item> carriedInactive = new ArrayList<>();

  public Item getCarriedLeft()
  {
    return carriedLeft;
  }

  public void setCarriedLeft(Item carriedLeft)
  {
    this.carriedLeft = carriedLeft;
  }

  public Item getCarriedRight()
  {
    return carriedRight;
  }

  public void setCarriedRight(Item carriedRight)
  {
    this.carriedRight = carriedRight;
  }

  public List<Item> getWorn()
  {
    if (null == worn)
    {
      worn = new ArrayList<>();
    }
    return worn;
  }

  public boolean isWorn(Item item)
  {
    return getWorn().contains(item);
  }

  public List<Item> getCarriedInactive()
  {
    return carriedInactive;
  }

  public int countTotalCarried()
  {
    int total = 0;
    if (carriedLeft != null)
    {
      total++;
    }

    if (carriedRight != null)
    {
      total++;
    }

    total += getWorn().size();
    total += carriedInactive.size();

    return total;
  }
}
