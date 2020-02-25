package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.trinkets.consumable.*;

import java.util.*;

public class TrinketFactory
{
  private final Map<Class<? extends Trinket>, Integer> trinkets;

  private static final int VERY_RARE = 1;
  private static final int RARE = 2;
  private static final int QUITE_RARE = 8;
  private static final int COMMON = 24;

  private static TrinketFactory instance;
  private transient List<Class<? extends Trinket>> weightedTrinkets;

  private TrinketFactory()
  {
    trinkets = new HashMap<>();
    trinkets.put(BeadsOfPower.class, RARE);
    trinkets.put(AmuletOfPower.class, VERY_RARE);
    trinkets.put(BeadsOfProtection.class, RARE);
    trinkets.put(AmuletOfProtection.class, VERY_RARE);
    trinkets.put(WardOfAlacrity.class, RARE);
    trinkets.put(LootBag.class, VERY_RARE);
    trinkets.put(WardOfViolence.class, VERY_RARE);
    trinkets.put(CloakOfShadows.class, VERY_RARE);
    trinkets.put(ManaRing.class, RARE);
    trinkets.put(HealingWard.class, RARE);

    trinkets.put(LesserHealingPotion.class, COMMON);
    trinkets.put(GreaterHealingPotion.class, QUITE_RARE);
    trinkets.put(MassiveHealingPotion.class, RARE);
    trinkets.put(LesserManaPotion.class, COMMON);
    trinkets.put(GreaterManaPotion.class, QUITE_RARE);

    buildWeightedTrinkets();
  }

  public static TrinketFactory instance()
  {
    if (instance == null)
    {
      createInstance();
    }
    return instance;
  }

  private synchronized static void createInstance()
  {
    if (instance != null)
    {
      return;
    }
    instance = new TrinketFactory();
  }

  public Trinket create()
  {
    int ind = (int) (Math.random() * weightedTrinkets.size());
    Class<? extends Trinket> trinketClass = weightedTrinkets.get(ind);
    try
    {
      return trinketClass.newInstance();
    }
    catch (InstantiationException | IllegalAccessException e)
    {
      throw new WoodlandsRuntimeException("Cannot instantiate trinket '" + trinketClass.getSimpleName() + "'", e);
    }
  }

  private void buildWeightedTrinkets()
  {
    weightedTrinkets = new ArrayList<>();
    for (Map.Entry<Class<? extends Trinket>, Integer> trinketChance : trinkets.entrySet())
    {
      for (int i = 0; i < trinketChance.getValue(); i++)
      {
        weightedTrinkets.add(trinketChance.getKey());
      }
    }
  }
}
