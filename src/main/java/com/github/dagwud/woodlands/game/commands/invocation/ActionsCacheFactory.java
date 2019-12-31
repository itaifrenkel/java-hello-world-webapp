package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.ActionsCache;
import com.github.dagwud.woodlands.gson.Root;
import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.adapter.TestJSON;

public class ActionsCacheFactory
{
  private static ActionsCacheFactory instance;
  private final ActionsCache actions;

  private ActionsCacheFactory()
  {
    Root root = GsonHelper.readJSON(TestJSON.TEST, Root.class);
    actions = new ActionsCache(root);
  }

  public static ActionsCacheFactory instance()
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
      instance = new ActionsCacheFactory();
    }
  }

  protected ActionsCache getActions()
  {
    return actions;
  }
}
