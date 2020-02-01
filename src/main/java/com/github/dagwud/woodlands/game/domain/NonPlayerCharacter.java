package com.github.dagwud.woodlands.game.domain;

public abstract class NonPlayerCharacter extends GameCharacter
{
  private static final long serialVersionUID = 1L;
  private final Player ownedBy;

  NonPlayerCharacter(Player ownedBy)
  {
    this.ownedBy = ownedBy;
  }

  @Override
  public boolean isActive()
  {
    return !isDead();
  }

  public Player getOwnedBy()
  {
    return ownedBy;
  }
}
