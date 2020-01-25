package com.github.dagwud.woodlands.game.domain;

import java.util.ArrayList;
import java.util.Iterator;
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

  public List<GameCharacter> getActiveMembers()
  {
    List<GameCharacter> active = new ArrayList<>(members.size());
    for (GameCharacter member : members)
    {
      if (member.isActive())
      {
        active.add(member);
      }
    }
    return active;
  }

  public boolean isLedBy(GameCharacter activeCharacter)
  {
    return getLeader() == activeCharacter;
  }

  public int size()
  {
    int count = 0;
    for (GameCharacter c : members)
    {
      if (c.isActive())
      {
        count++;
      }
    }
    return count;
  }

  public boolean isPrivateParty()
  {
    return getName().startsWith("_");
  }

  public GameCharacter getLeader()
  {
    Iterator<GameCharacter> it = members.iterator();
    while (it.hasNext())
    {
      GameCharacter member = it.next();
      if (member.isActive())
      {
        return member;
      }
    }
   return null;
  }

  public boolean capableOfRetreat()
  {
    return countConscious() >= (0.5 * size());
  }

  public boolean canAct()
  {
    return countConscious() > 0;
  }

  private int countConscious()
  {
    int conscious = 0;
    for (GameCharacter member : getActiveMembers())
    {
      if (member.isActive() && member.getStats().getState() == EState.ALIVE)
      {
        conscious++;
      }
    }
    return conscious;
  }
}
