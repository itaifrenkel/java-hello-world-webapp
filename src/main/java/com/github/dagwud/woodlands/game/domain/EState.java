package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.Icons;

public enum EState
{
  SHORT_RESTING(Icons.SHORT_REST, "resting"),
  LONG_RESTING(Icons.LONG_REST, "resting"),
  ALIVE("", "alive"), // implicit - no icon
  DRINKING(Icons.DRUNK + "", "drinking"),
  UNCONSCIOUS(Icons.UNCONSCIOUS, "unconscious"),
  DEAD(Icons.DEAD + "️", "dead");

  public final String icon;
  public final String description;

  EState(String icon, String description)
  {
    this.icon = icon;
    this.description = description;
  }
}
