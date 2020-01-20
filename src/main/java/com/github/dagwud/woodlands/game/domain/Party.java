package com.github.dagwud.woodlands.game.domain;

import java.util.ArrayList;
import java.util.Collection;

public class Party
{
  private Collection<GameCharacter> members = new ArrayList<>(4);
  private String name;

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public Collection<GameCharacter> getMembers()
  {
    return members;
  }
}
