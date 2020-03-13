package com.github.dagwud.woodlands.game.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class FightingGroup implements Serializable
{
  private static final long serialVersionUID = 1L;

  protected abstract Collection<Fighter> getMembers();

  public List<Fighter> getActiveMembers()
  {
    List<Fighter> active = new ArrayList<>(getMembers().size());
    for (Fighter member : getMembers())
    {
      if (member.isActive())
      {
        active.add(member);
      }
    }
    return active;
  }

  public List<PlayerCharacter> getActivePlayerCharacters()
  {
    List<PlayerCharacter> active = new ArrayList<>();
    for (Fighter activeMember : getActiveMembers())
    {
      if (activeMember instanceof PlayerCharacter)
      {
        active.add((PlayerCharacter)activeMember);
      }
    }
    return active;
  }

  public boolean capableOfRetreat()
  {
    return countConscious() >= (0.5 * size());
  }

  public int size()
  {
    return getActiveMembers().size();
  }

  public Fighter getLeader()
  {
    for (Fighter member : getActivePlayerCharacters())
    {
      return member;
    }
    return null;
  }

  public boolean canAct()
  {
    return countConscious() > 0;
  }

  public void removeDeadNPCs()
  {
    Collection<NonPlayerCharacter> toRemove = new ArrayList<>(1);
    for (Fighter member : getMembers())
    {
      if (member instanceof NonPlayerCharacter && member.isDead())
      {
        toRemove.add((NonPlayerCharacter) member);
      }
    }
    for (NonPlayerCharacter nonPlayerCharacter : toRemove)
    {
      removeMember(nonPlayerCharacter);
    }
  }

  protected abstract void removeMember(GameCharacter leaver);

  private int countConscious()
  {
    int conscious = 0;
    for (Fighter member : getActiveMembers())
    {
      if (member.isActive() && member.isConscious())
      {
        conscious++;
      }
    }
    return conscious;
  }
}
