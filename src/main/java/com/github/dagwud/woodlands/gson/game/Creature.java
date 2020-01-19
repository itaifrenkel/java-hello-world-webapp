package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.domain.CarriedItems;
import com.github.dagwud.woodlands.game.domain.IFighter;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.game.items.ItemsCacheFactory;
import com.github.dagwud.woodlands.game.items.UnknownWeaponException;
import com.google.gson.annotations.SerializedName;

public class Creature implements IFighter
{
  public String name;

  public int difficulty;

  @SerializedName("weapon-left")
  public String weaponLeft;

  private Stats stats;
  private CarriedItems carriedItems;

  public Creature()
  {
  }

  public Creature(Creature template)
  {
    this();
    this.name = template.name;
    this.difficulty = template.difficulty;
    this.weaponLeft = template.weaponLeft;
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
      carriedItems = new CarriedItems();
      try
      {
        carriedItems.setCarriedLeft(ItemsCacheFactory.instance().getCache().findWeapon(weaponLeft));
      }
      catch (UnknownWeaponException e)
      {
        throw new WoodlandsRuntimeException("Bad config for creature " + name, e);
      }
    }
    return carriedItems;
  }

  public void setStats(Stats stats)
  {
    this.stats = stats;
  }
}
