package com.github.dagwud.woodlands.game.creatures;

import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.game.DifficultyRoot;

import java.io.File;

public class DifficultyCacheFactory
{
  private static final String ITEMS_FILE = "src/main/resources/difficulties.json";

  private static DifficultyCacheFactory instance;

  private final DifficultyCache cache;

  private DifficultyCacheFactory()
  {
    DifficultyRoot root = GsonHelper.readJSON(new File(ITEMS_FILE), DifficultyRoot.class);
    cache = new DifficultyCache(root);
  }

  public static DifficultyCacheFactory instance()
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
      instance = new DifficultyCacheFactory();
    }
  }

  public DifficultyCache getCache()
  {
    return cache;
  }

}
