package com.github.dagwud.woodlands.game.domain;

public abstract class NonPlayerCharacter extends GameCharacter
{
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
