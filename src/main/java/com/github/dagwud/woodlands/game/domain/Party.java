package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.Settings;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class Party implements Serializable
{
  private static final long serialVersionUID = 1L;

  private List<GameCharacter> members = new ArrayList<>(4);
  private String name;
  private Encounter encounter;
  private BigDecimal percentChanceOfEncounter;
  private List<Item> collectedItems;
  private Long alertChatId;
  private Blacksmith blacksmith;

  public Party()
  {
    percentChanceOfEncounter = Settings.DEFAULT_PERCENT_CHANCE_OF_ENCOUNTER;
  }

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

  public List<GameCharacter> getAllMembers()
  {
    return Collections.unmodifiableList(members);
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

  public List<PlayerCharacter> getActivePlayerCharacters()
  {
    List<PlayerCharacter> active = new ArrayList<>();
    for (GameCharacter activeMember : getActiveMembers())
    {
      if (activeMember instanceof PlayerCharacter)
      {
        active.add((PlayerCharacter)activeMember);
      }
    }
    return active;
  }

  public boolean isLedBy(PlayerCharacter activeCharacter)
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
      if (member.isActive() && member.isConscious())
      {
        conscious++;
      }
    }
    return conscious;
  }

  public BigDecimal getPercentChanceOfEncounter()
  {
    return percentChanceOfEncounter;
  }

  public void setPercentChanceOfEncounter(BigDecimal percentChanceOfEncounter)
  {
    this.percentChanceOfEncounter = percentChanceOfEncounter;
  }

  public void removeDeadNPCs()
  {
    Collection<NonPlayerCharacter> toRemove = new ArrayList<>(1);
    for (GameCharacter member : members)
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

  public List<Item> getCollectedItems()
  {
    if (null == collectedItems)
    {
      collectedItems = new LinkedList<>();
    }
    return collectedItems;
  }

  public Long getAlertChatId()
  {
    return alertChatId;
  }

  public void setAlertChatId(Long alertChatId)
  {
    this.alertChatId = alertChatId;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Party party = (Party) o;
    return Objects.equals(name, party.name);
  }

  @Override
  public int hashCode()
  {

    return Objects.hash(name);
  }

  public boolean allInVillage()
  {
    for (GameCharacter activeMember : getActiveMembers())
    {
      if (!activeMember.getLocation().isVillageLocation())
      {
        return false;
      }
    }
    return true;
  }

  public Blacksmith getBlacksmith()
  {
    if (blacksmith == null)
    {
      blacksmith = new Blacksmith();
    }
    return blacksmith;
  }
}
