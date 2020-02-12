package com.github.dagwud.woodlands.game.domain;

import com.google.gson.annotations.SerializedName;

public abstract class Item extends GameObject
{
  private static final long serialVersionUID = 1L;

  @SerializedName(value = "prevent_spawning")
  public boolean preventSpawning;

  public abstract String getName();

  public abstract String getIcon();

  public abstract String summary(Fighter carrier, boolean includeName);

  public final String summary(Fighter carrier)
  {
    return summary(carrier, true);
  }

  public abstract String statsSummary(Fighter carrier);

  public abstract void doEquip(Fighter character);
}
