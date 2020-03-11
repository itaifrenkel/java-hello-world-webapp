package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.domain.CarriedItems;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.game.items.UnknownWeaponException;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.util.*;

public class Creature extends Fighter
{
  public static final String CREATURE_TYPE_DRAGON = "dragon";
  public static final String CREATURE_TYPE_NORMAL = "normal";

  private static final long serialVersionUID = 1L;
  public String name;

  public double difficulty;

  @SerializedName("weapon-left")
  public String weaponLeft;

  @SerializedName("weapon-right")
  public String weaponRight;

  @SerializedName("fight-mode")
  public String fightMode = "RANDOM";

  @SerializedName("creature-type")
  public String creatureType;

  private Stats stats;
  private CarriedItems carriedItems;

  public Creature(Creature template)
  {
    this.name = template.name;
    this.difficulty = template.difficulty;
    this.weaponLeft = template.weaponLeft;
    this.fightMode = template.fightMode;
    this.creatureType = template.creatureType;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public Stats getStats()
  {
    return stats;
  }

  @Override
  public CarriedItems getCarrying()
  {
    if (carriedItems == null)
    {
      populateCarriedItems();
    }
    return carriedItems;
  }

  private void populateCarriedItems()
  {
    carriedItems = new CarriedItems();
    try
    {
      if (null != weaponLeft)
      {
        carriedItems.setCarriedLeft(ItemsCacheFactory.instance().getCache().findWeapon(weaponLeft));
      }
      if (null != weaponRight)
      {
        carriedItems.setCarriedRight(ItemsCacheFactory.instance().getCache().findWeapon(weaponRight));
      }
    }
    catch (UnknownWeaponException e)
    {
      throw new WoodlandsRuntimeException("Bad config for creature " + name, e);
    }
  }

  public void setStats(Stats stats)
  {
    this.stats = stats;
  }

  @Override
  public Fighter chooseFighterToAttack(Collection<? extends Fighter> members)
  {
    List<Fighter> targets = new ArrayList<>(members);
    targets.removeIf(f -> !(f instanceof GameCharacter));
    targets.removeIf(f -> !f.isConscious());
    if (targets.isEmpty())
    {
      return null;
    }

    switch (fightMode)
    {
      case "RANDOM":
        Collections.shuffle(targets);
        return targets.get(0);
      case "HEALTHIEST":
        targets.sort(Comparator.comparingInt(o1 -> o1.getStats().getHitPoints()));
        return targets.get(targets.size() - 1);
      case "SICKEST":
        targets.sort(Comparator.comparingInt(o -> o.getStats().getHitPoints()));
        return targets.get(0);
      case "STRONGEST":
        targets.sort(Comparator.comparing(o -> o.getStats().getStrength().total()));
        return targets.get(targets.size() - 1);
      case "WEAKEST":
        targets.sort(Comparator.comparing(o -> o.getStats().getStrength().total()));
        return targets.get(0);
      case "DEFAULT":
        return targets.get(0);
      default:
        throw new WoodlandsRuntimeException("Unknown fight mode '" + fightMode + "'");
    }
  }

  @Override
  public boolean isActive()
  {
    return true;
  }

  public String difficulty()
  {
    return new DecimalFormat("0.#").format(difficulty);
  }
}
