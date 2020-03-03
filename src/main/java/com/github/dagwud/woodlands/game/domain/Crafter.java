package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.HashMap;
import java.util.Map;

public class Crafter<T extends Item> extends NonPlayerCharacter
{
  private static final long serialVersionUID = 1L;
  private boolean busyCrafting;
  private Map<PlayerCharacter, T> readyForCollection;

  Crafter(Player ownedBy)
  {
    super(ownedBy);
  }

  public void setBusyCrafting(boolean busyCrafting)
  {
    this.busyCrafting = busyCrafting;
  }

  public boolean isBusyCrafting()
  {
    return busyCrafting;
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
