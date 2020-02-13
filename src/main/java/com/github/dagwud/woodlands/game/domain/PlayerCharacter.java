package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.trinkets.LootBag;
import com.github.dagwud.woodlands.game.log.Logger;
import java.util.List;
import java.util.ArrayList;

public abstract class PlayerCharacter extends GameCharacter
{
  private static final long serialVersionUID = 1L;
  private final Player playedBy;
  private int playerChatId;
  private final ECharacterClass characterClass;
  private boolean setupComplete;
  private List<Fighter> recentlyDefeated;

  public PlayerCharacter(Player playedBy, ECharacterClass characterClass)
  {
    this.playedBy = playedBy;
    this.playerChatId = player.getChatId();
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

  @Override
  public int determineMaxAllowedItems()
  {
    int base = 3 + Math.floorDiv(getStats().getLevel(), 2);
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
    if (null == playedBy)
    {
      playedBy = GameStatesRegistry.lookup(playerChatId).getPlayer();
    }
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
