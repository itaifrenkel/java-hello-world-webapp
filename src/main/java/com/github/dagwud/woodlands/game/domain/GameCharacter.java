package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.stats.Stats;

public abstract class GameCharacter extends Fighter
{
  private final Stats stats;
  private final CarriedItems carrying;
  private Party party;
  private String name;
  private ELocation location;

  public GameCharacter()
  {
    stats = new Stats();
    stats.setState(EState.ALIVE);
    carrying = new CarriedItems();
  }

  public ELocation getLocation()
  {
    return location;
  }

  @Override
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Override
  public Stats getStats()
  {
    return stats;
  }

  public void setLocation(ELocation location)
  {
    this.location = location;
  }

  public Party getParty()
  {
    return party;
  }

  public void setParty(Party party)
  {
    this.party = party;
  }

  public abstract boolean isActive();

  @Override
  public CarriedItems getCarrying()
  {
    return carrying;
  }
}
