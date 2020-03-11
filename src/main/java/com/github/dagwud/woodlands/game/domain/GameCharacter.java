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
  public Fighter chooseFighterToAttack(Collection<? extends Fighter> fighters)
  {
    return chooseFighterToAttack(fighters, false, null);
  }

  public Fighter chooseFighterToAttack(Collection<? extends Fighter> fighters, boolean allowFriendlyFire, Fighter attacker)
  {
    List<Fighter> enemies = new ArrayList<>();
    for (Fighter fighter : fighters)
    {
      boolean isCandidate = (allowFriendlyFire && fighter instanceof PlayerCharacter)
                            || (!allowFriendlyFire && fighter instanceof Creature);
      isCandidate = isCandidate && fighter.isConscious();
      isCandidate = isCandidate && (fighter != attacker);
      if (isCandidate)
      {
        enemies.add(fighter);
      }
    }

    if (enemies.isEmpty())
    {
      return null;
    }

    int i = (int) (Math.random() * enemies.size());
    return enemies.get(i);
  }

  // completely checked, if you check
  @SuppressWarnings("unchecked")
  public <T> List<T> produceItems(Class<T> ofClass)
  {
    List<T> potions = new ArrayList<>();

    if (getCarrying().getCarriedLeft() != null && ofClass.isAssignableFrom(getCarrying().getCarriedLeft().getClass()))
    {
      potions.add((T) getCarrying().getCarriedLeft());
    }
    if (getCarrying().getCarriedRight() != null && ofClass.isAssignableFrom(getCarrying().getCarriedRight().getClass()))
    {
      potions.add((T) getCarrying().getCarriedRight());
    }
    for (Item inactive : getCarrying().getCarriedInactive())
    {
      if (ofClass.isAssignableFrom(inactive.getClass()))
      {
        potions.add((T) inactive);
      }
    }

    return potions;
  }
}
