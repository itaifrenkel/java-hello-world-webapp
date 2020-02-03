package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Item;

public class Shield extends Item
{
  private static final long serialVersionUID = 1L;

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
  public String summary(Fighter carrier, boolean includeName)
  {
    return (includeName ? getName() + " " : "") + getIcon() + statsSummary(carrier);
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return String.valueOf(strength);
  }
}
