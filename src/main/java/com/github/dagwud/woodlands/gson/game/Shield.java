package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.items.EquippableItem;

public class Shield extends EquippableItem
{
  private static final long serialVersionUID = 1L;

  public String name;
  public int strength;
  public boolean enchanted;

  @Override
  public String getName()
  {
    return (enchanted ? "Enchanted " : "") + name;
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
