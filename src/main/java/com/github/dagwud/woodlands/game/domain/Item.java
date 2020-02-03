package com.github.dagwud.woodlands.game.domain;

import java.io.Serializable;

public abstract class Item implements Serializable
{
  private static final long serialVersionUID = 1L;

  public abstract String getName();

  public abstract String getIcon();

  public abstract String summary(Fighter carrier);
}
