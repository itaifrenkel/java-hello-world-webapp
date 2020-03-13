package com.github.dagwud.woodlands.game.domain;

import java.util.Collection;
import java.util.Collections;

public class SingleFighter extends FightingGroup
{
  private static final long serialVersionUID = 1L;
  private final GameCharacter fighter;

  public SingleFighter(GameCharacter fighter)
  {
    this.fighter = fighter;
  }

  @Override
  protected Collection<Fighter> getMembers()
  {
    return Collections.singletonList(fighter);
  }

  @Override
  protected void removeMember(GameCharacter leaver)
  {
    throw new UnsupportedOperationException("SingleFighter can't have its only fighter removed");
  }
}
