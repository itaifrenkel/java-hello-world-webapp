package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.domain.npc.Alchemist;
import com.github.dagwud.woodlands.game.domain.npc.Blacksmith;
import com.github.dagwud.woodlands.game.domain.npc.PokerDealer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class Party extends FightingGroup implements Serializable
{
  private static final long serialVersionUID = 1L;

  private List<Fighter> members = new ArrayList<>(4);
  private String name;
  private Encounter encounter;
  private BigDecimal percentChanceOfEncounter;
  private List<Item> collectedItems;
  private Long alertChatId;
  private Blacksmith blacksmith;
  private Alchemist alchemist;

  private PokerDealer pokerDealer;

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

  public List<Fighter> getAllMembers()
  {
    return Collections.unmodifiableList(members);
  }

  public boolean isLedBy(PlayerCharacter activeCharacter)
  {
    return getLeader() == activeCharacter;
  }

  public boolean isPrivateParty()
  {
    return getName().startsWith("_");
  }

  public BigDecimal getPercentChanceOfEncounter()
  {
    return percentChanceOfEncounter;
  }

  public void setPercentChanceOfEncounter(BigDecimal percentChanceOfEncounter)
  {
    this.percentChanceOfEncounter = percentChanceOfEncounter;
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
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
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
    for (Fighter activeMember : getActiveMembers())
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

  public PokerDealer getPokerDealer()
  {
    if (pokerDealer == null)
    {
      pokerDealer = new PokerDealer();
    }

    return pokerDealer;
  }

  public Alchemist getAlchemist()
  {
    if (alchemist == null)
    {
      alchemist = new Alchemist();
    }
    return alchemist;
  }

  public void changeLeader(GameCharacter newLeader)
  {
    if (!members.contains(newLeader))
    {
      // prevent very bad things happening
      return;
    }
    members.remove(newLeader);
    members.add(0, newLeader);
  }

  public List<PlayerCharacter> findPlayerCharactersIn(ELocation location)
  {
    List<PlayerCharacter> found = new ArrayList<>(2);
    for (PlayerCharacter activePlayerCharacter : getActivePlayerCharacters())
    {
      if (activePlayerCharacter.getLocation() == location)
      {
        found.add(activePlayerCharacter);
      }
    }
    return found;
  }

  @Override
  protected Collection<Fighter> getMembers()
  {
    return members;
  }
}
