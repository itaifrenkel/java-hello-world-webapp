package com.github.dagwud.woodlands.game.domain;

public class Peasant extends NonPlayerCharacter
{
  public Peasant(Player ownedBy, int hitPoints, String name)
  {
    super(ownedBy);
    setName(name);
    getStats().setHitPoints(hitPoints);
    getStats().setMaxHitPoints(hitPoints);
  }
}
