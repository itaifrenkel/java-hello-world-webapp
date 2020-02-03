package com.github.dagwud.woodlands.game.domain;

public abstract class Item extends GameObject
{
  private static final long serialVersionUID = 1L;

  public abstract String getName();

  public abstract String getIcon();

  public abstract String summary(Fighter carrier);
}
