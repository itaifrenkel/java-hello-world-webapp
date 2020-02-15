package com.github.dagwud.woodlands.game.domain;

public enum EState
{
  RESTING("🛏️"),
  ALIVE(""),
  UNCONSCIOUS("💔"),
  DEAD("☠️");

  private final String icon;

  EState(String icon)
  {
    this.icon = icon;
  }
}
