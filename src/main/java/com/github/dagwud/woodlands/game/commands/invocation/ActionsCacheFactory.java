package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.ActionsCache;
import com.github.dagwud.woodlands.gson.game.Root;
import com.github.dagwud.woodlands.gson.adapter.GsonHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ActionsCacheFactory
{
  private static final String ACTIONS_FILE = "src/main/resources/actions.json";

  private static ActionsCacheFactory instance;

  private final ActionsCache actions;

  private ActionsCacheFactory()
  {
    Root root = GsonHelper.readJSON(new File(ACTIONS_FILE), Root.class);
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

  public ActionsCache getActions()
  {
    return actions;
  }
}
