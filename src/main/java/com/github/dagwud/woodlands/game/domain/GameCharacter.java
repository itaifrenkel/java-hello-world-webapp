package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.stats.Stats;

public abstract class GameCharacter extends Fighter
{
  private final Player playedBy;
  private final ECharacterClass characterClass;
  private String name;
  private Stats stats;
  private ELocation location;
  private CarriedItems carrying = new CarriedItems();
  private boolean setupComplete;
  private Party party;

  public GameCharacter(Player playedBy, ECharacterClass characterClass)
  {
    this.playedBy = playedBy;
    this.characterClass = characterClass;
  }

  public ELocation getLocation()
  {
    return location;
  }

  public void setStats(Stats stats)
  {
    this.stats = stats;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public ECharacterClass getCharacterClass()
  {
    return characterClass;
  }

  public void setLocation(ELocation location)
  {
    this.location = location;
  }

  @Override
  public Stats getStats()
  {
    return stats;
  }

  public void setSetupComplete(boolean setupComplete)
  {
    this.setupComplete = setupComplete;
  }

  public boolean isSetupComplete()
  {
    return setupComplete;
  }

  public CarriedItems getCarrying()
  {
    return carrying;
  }

  public Party getParty()
  {
    return party;
  }

  public void setParty(Party party)
  {
    this.party = party;
  }

  public Player getPlayedBy()
  {
    return playedBy;
  }

  public boolean isActive()
  {
    return this == getPlayedBy().getActiveCharacter();
  }
}
