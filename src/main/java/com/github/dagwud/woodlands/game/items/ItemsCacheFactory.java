package com.github.dagwud.woodlands.game.items;

import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.game.ItemsRoot;
import com.github.dagwud.woodlands.gson.game.Root;

import java.io.File;

public class ItemsCacheFactory
{
  private static final String ITEMS_FILE = "src/main/resources/items.json";

  private static ItemsCacheFactory instance;

  private final ItemsCache items;

  private ItemsCacheFactory()
  {
    ItemsRoot root = GsonHelper.readJSON(new File(ITEMS_FILE), ItemsRoot.class);
    items = new ItemsCache(root);
  }

  static ItemsCacheFactory instance()
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
      instance = new ItemsCacheFactory();
    }
  }

  ItemsCache getItems()
  {
    return items;
  }

}
