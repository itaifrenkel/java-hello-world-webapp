package com.github.dagwud.woodlands.game.domain;

public class GameCharacter
{
  private String name;
  private ECharacterClass characterClass;
  private Stats stats;
  private ELocation location;
  private CarriedItems carrying = new CarriedItems();
  private boolean setupComplete;

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

  public void setCharacterClass(ECharacterClass characterClass)
  {
    this.characterClass = characterClass;
  }

  public void setLocation(ELocation location)
  {
    this.location = location;
  }

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

  public void setCarrying(CarriedItems carrying)
  {
    this.carrying = carrying;
  }
}
