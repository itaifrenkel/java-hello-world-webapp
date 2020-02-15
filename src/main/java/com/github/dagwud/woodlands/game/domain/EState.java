package com.github.dagwud.woodlands.game.domain;

public enum EState
{
  SHORT_RESTING("😴", "resting"),
  LONG_RESTING("🛏️", "resting"),
  ALIVE("", "alive"), // implicit - no icon
  UNCONSCIOUS("💔", "unconscious"),
  DEAD("☠️", "dead");

  public final String icon;
  public final String description;

  EState(String icon, String description)
  {
    this.icon = icon;
    this.description = description;
  }
}
