package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class GameCharacter extends Fighter
{
  private static final long serialVersionUID = 1L;
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
    //return name + "[" + Integer.toHexString(hashCode()).substring(4) + "]";
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
    if (this.location != null)
    {
      this.location.getCharactersInRoom().remove(this);
    }
    this.location = location;
    this.location.getCharactersInRoom().add(this);
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

  @Override
  public Creature chooseFighterToAttack(Collection<? extends Fighter> fighters)
  {
    List<Creature> creatures = new ArrayList<>();
    for (Fighter fighter : fighters)
    {
      if (fighter instanceof Creature)
      {
        creatures.add((Creature) fighter);
      }
    }
    int i = (int)(Math.random() * creatures.size());
    return creatures.get(i);
  }

}
