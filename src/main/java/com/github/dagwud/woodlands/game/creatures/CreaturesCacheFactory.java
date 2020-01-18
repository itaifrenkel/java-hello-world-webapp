package com.github.dagwud.woodlands.game.creatures;

import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.game.CreaturesRoot;

import java.io.File;

public class CreaturesCacheFactory
{
  private static final String ITEMS_FILE = "src/main/resources/creatures.json";

  private static CreaturesCacheFactory instance;

  private final CreaturesCache cache;

  private CreaturesCacheFactory()
  {
    CreaturesRoot root = GsonHelper.readJSON(new File(ITEMS_FILE), CreaturesRoot.class);
    cache = new CreaturesCache(root);
  }

  public static CreaturesCacheFactory instance()
  {
    if (null == instance)
    {
      createInstance();
    }
    return instance;
  }

  private static synchronized void createInstance()
  {
    if (null == instance)
    {
      instance = new CreaturesCacheFactory();
    }
  }

  public CreaturesCache getCache()
  {
    return cache;
  }

}
