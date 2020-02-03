package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Item;

public class Shield extends Item
{
  private static final long serialVersionUUID = 1L;

  public String name;
  public int strength;

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public String getIcon()
  {
    return "\uD83D\uDEE1";
  }

  @Override
  public String summary(Fighter carrier)
  {
    return carrier.getName() + " " + getIcon() + getName();
  }
}
