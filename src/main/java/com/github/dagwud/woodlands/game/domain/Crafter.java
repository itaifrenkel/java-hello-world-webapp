package com.github.dagwud.woodlands.game.domain;

import java.util.HashMap;
import java.util.Map;

public class Crafter<T extends Item> extends NonPlayerCharacter
{
  private static final long serialVersionUID = 1L;
  private Map<PlayerCharacter, T> busyWithCrafting;
  private Map<PlayerCharacter, T> readyForCollection;
  
  Crafter(Player ownedBy)
  {
    super(ownedBy);
  }

  public void completeCrafting(PlayerCharacter character)
  {
    T remove = busyWithCrafting.remove(character);
    if (remove != null)
    {
      readyForCollection.put(character, remove);
    }
  }

  public void setBusyCrafting(PlayerCharacter character, T item)
  {
    getBusyCrafting().put(character, item);
  }

  public boolean isBusyCrafting()
  {
    return !getBusyCrafting().isEmpty();
  }

  public Map<PlayerCharacter, T> getBusyCrafting()
  {
    if (busyWithCrafting == null)
    {
      busyWithCrafting = new HashMap<>();
    }
    return busyWithCrafting;
  }

  public T collectFor(PlayerCharacter craftedFor)
  {
    return getReadyForCollection().remove(craftedFor);
  }

  public void addReadyForCollection(T weapon, PlayerCharacter craftedFor)
  {
    getReadyForCollection().put(craftedFor, weapon);
  }

  private Map<PlayerCharacter, T> getReadyForCollection()
  {
    if (readyForCollection == null)
    {
      readyForCollection = new HashMap<>();
    }
    return readyForCollection;
  }

}
