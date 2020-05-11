package com.github.dagwud.woodlands.game.domain.npc;

import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

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
    return true;
  }

  public Player getOwnedBy()
  {
    return ownedBy;
  }
}
