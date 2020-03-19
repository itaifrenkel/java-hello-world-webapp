package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.domain.trinkets.LootBag;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.List;
import java.util.ArrayList;

public abstract class PlayerCharacter extends GameCharacter
{
  private static final long serialVersionUID = 1L;
  private transient Player playedBy;
  private int playerChatId;
  private final ECharacterClass characterClass;
  private boolean setupComplete;
  private List<Fighter> recentlyDefeated;
  private Innkeeper innkeeper;

  public PlayerCharacter(Player playedBy, ECharacterClass characterClass)
  {
    this.playerChatId = playedBy.getChatId();
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
    int base = 5 + Math.floorDiv(getStats().getLevel(), 2);
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

  public Innkeeper getInnkeeper()
  {
    if (null == innkeeper)
    {
      innkeeper = new Innkeeper(getPlayedBy());
    }
    return innkeeper;
  }

  public boolean shouldGainExperienceByDefeating(Creature enemy)
  {
    double levelDiff = getStats().getLevel() - enemy.difficulty;
    return levelDiff <= 2.0;
  }

  public boolean canHandleWeapon(Weapon weapon)
  {
    double percentage = determinePercentageOfMaxDamageThatPlayerCanHandle();
    double maxDamageAllowed = ((double)Settings.MAX_CRAFTABLE_WEAPON_DAMAGE) * percentage / 100d;
    return weapon.damage.determineAverageRollAmount() <= maxDamageAllowed;
  }

  private double determinePercentageOfMaxDamageThatPlayerCanHandle()
  {
    if (getStats().getLevel() >= 13)
    {
      return 100;
    }
    if (getStats().getLevel() >= 12)
    {
      return 95;
    }
    if (getStats().getLevel() >= 11)
    {
      return 90;
    }
    if (getStats().getLevel() >= 10)
    {
      return 75;
    }
    if (getStats().getLevel() >= 9)
    {
      return 60;
    }
    if (getStats().getLevel() >= 8
    {
      return 50;
    }
    if (getStats().getLevel() >= 7)
    {
      return 40;
    }
    if (getStats().getLevel() >= 6)
    {
      return 30;
    }
    if (getStats().getLevel() >= 5)
    {
      return 20;
    }
    if (getStats().getLevel() >= 4)
    {
      return 15;
    }
    if (getStats().getLevel() >= 4)
    {
      return 10;
    }
    if (getStats().getLevel() >= 3)
    {
      return 8;
    }
    if (getStats().getLevel() >= 2)
    {
      return 7;
    }
    return 0; // effectively no crafting
  }
}
