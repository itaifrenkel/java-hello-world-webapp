package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;

import java.util.ArrayList;

public class TrinketFactory
{
  private final ArrayList<Class<? extends Trinket>> trinkets;
  private static TrinketFactory instance;

  private TrinketFactory()
  {
    trinkets = new ArrayList<>();
    trinkets.add(BeadsOfPower.class);
    trinkets.add(AmuletOfPower.class);
    trinkets.add(BeadsOfProtection.class);
    trinkets.add(AmuletOfProtection.class);
    trinkets.add(WardOfAlacrity.class);
    trinkets.add(LootBag.class);
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
    int ind = (int) (Math.random() * trinkets.size());
    Class<? extends Trinket> trinketClass = trinkets.get(ind);
    try
    {
      return trinketClass.newInstance();
    }
    catch (InstantiationException | IllegalAccessException e)
    {
      throw new WoodlandsRuntimeException("Cannot instantiate trinket '" + trinketClass.getSimpleName() + "'", e);
    }
  }
}
