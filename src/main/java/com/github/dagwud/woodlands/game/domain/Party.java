package com.github.dagwud.woodlands.game.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Party
{
  private List<GameCharacter> members = new ArrayList<>(4);
  private String name;
  private Encounter encounter;

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setActiveEncounter(Encounter encounter)
  {
    this.encounter = encounter;
  }

  public Encounter getActiveEncounter()
  {
    return encounter;
  }

  public void addMember(GameCharacter joiner)
  {
    members.add(joiner);
  }

  public void removeMember(GameCharacter leaver) 
  {
    members.remove(leaver);
    leaver.setParty(null);
  }
  
  public List<GameCharacter> getMembers()
  {
    // quick and easy synchronization protection:
    return new ArrayList<>(members);
  }

  public boolean isLedBy(GameCharacter activeCharacter)
  {
    return getLeader() == activeCharacter;
  }

  public boolean isPrivateParty()
  {
    return getName().startsWith("_");
  }

  public GameCharacter getLeader()
  {
    if (!members.iterator().hasNext())
    {
      return null;
    }
    return members.iterator().next();
  }
}
