package com.github.dagwud.woodlands.game.domain;

public class Peasant extends NonPlayerCharacter
{
  private static final long serialVersionUID = 1L;

  public Peasant(Player ownedBy, int hitPoints, String name)
  {
    super(ownedBy);
    setName(name);
    getStats().setHitPoints(hitPoints);
    getStats().getMaxHitPoints().setBase(hitPoints);
  }
}
