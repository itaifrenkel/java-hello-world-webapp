package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.trinkets.LootBag;
import com.github.dagwud.woodlands.game.log.Logger;
import java.util.List;
import java.util.ArrayList;

public abstract class PlayerCharacter extends GameCharacter
{
  private static final long serialVersionUID = 1L;
  private final Player playedBy;
  private final ECharacterClass characterClass;
  private boolean setupComplete;
  private List<Fighter> recentlyDefeated;

  public PlayerCharacter(Player playedBy, ECharacterClass characterClass)
  {
    this.playedBy = playedBy;
    this.characterClass = characterClass;
  }

  public ECharacterClass getCharacterClass()
  {
    return characterClass;
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
    int base = 7 + getStats().getLevel();
    for (Item item : getCarrying().getWorn())
    {
      if (item instanceof LootBag)
      {
        LootBag bag = (LootBag) item;
        base += bag.getProvidedSpaces();
      }
    }
    return base;
  }

  public Player getPlayedBy()
  {
    return playedBy;
  }

  public boolean isActive()
  {
    return this == getPlayedBy().getActiveCharacter();
  }

  public List<Fighter> getRecentlyDefeated()
  {
    if (recentlyDefeated == null)
    {
      recentlyDefeated = new ArrayList<>();
    }
    return recentlyDefeated;
  }
}
