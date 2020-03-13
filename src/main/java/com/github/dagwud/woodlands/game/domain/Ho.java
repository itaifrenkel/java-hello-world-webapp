package com.github.dagwud.woodlands.game.domain;

public class Ho extends NonPlayerCharacter
{
  private static final long serialVersionUID = 1L;

  public Ho(Player ownedBy, int hitPoints, String name)
  {
    super(ownedBy);
    setName(name);
    getStats().setHitPoints(hitPoints);
    getStats().getMaxHitPoints().setBase(hitPoints);
    getStats().setRestDiceFace(1);
    getStats().setLevel(1);
  }
}
