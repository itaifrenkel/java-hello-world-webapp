package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

import java.util.Collection;

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

  public int determineMaxAllowedItems()
  {
    maxAllowedItems = 7 + getStats().getLevel();
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

  public abstract Collection<? extends Spell> castPassives();
}
