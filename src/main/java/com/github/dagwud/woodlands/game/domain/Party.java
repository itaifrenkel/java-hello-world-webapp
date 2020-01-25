package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.domain.stats.EState;

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
  
  public List<GameCharacter> getMembers()
  {
    // quick and easy synchronization protection:
    return new ArrayList<>(members);
  }

  public boolean isLedBy(GameCharacter activeCharacter)
  {
    return getLeader() == activeCharacter;
  }

  public int size()
  {
    return members.size();
  }

  public boolean isPrivateParty()
  {
    return getName().startsWith("_");
  }

  public GameCharacter getLeader()
  {
    Iterator<GameCharacter> it = members.iterator();
    while (it.hasNext)
    {
      GameCharacter member = it.next();
      if (member.getStats().getState != EState.INACTIVE)
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
    for (GameCharacter member : getMembers())
    {
      if (member.getStats().getState() == EState.ALIVE)
      {
        conscious++;
      }
    }
    return conscious;
  }
}
