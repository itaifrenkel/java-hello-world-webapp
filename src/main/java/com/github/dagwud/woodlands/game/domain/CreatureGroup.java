package com.github.dagwud.woodlands.game.domain;

import java.util.Collection;
import java.util.List;

public class CreatureGroup extends FightingGroup
{
  private static final long serialVersionUID = 1L;
  private final List<Fighter> creatures;

  public CreatureGroup(List<Fighter> creatures)
  {
    this.creatures = creatures;
  }

  @Override
  protected Collection<Fighter> getMembers()
  {
    return creatures;
  }

  @Override
  protected void removeMember(GameCharacter leaver)
  {

  }
}
