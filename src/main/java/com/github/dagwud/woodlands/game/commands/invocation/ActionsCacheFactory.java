package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.ActionsCache;
import com.github.dagwud.woodlands.game.commands.UnknownActionException;
import com.github.dagwud.woodlands.game.commands.quickcommands.QuickCommandsCache;
import com.github.dagwud.woodlands.gson.game.Action;
import com.github.dagwud.woodlands.gson.game.QuickCommand;
import com.github.dagwud.woodlands.gson.game.Root;
import com.github.dagwud.woodlands.gson.adapter.GsonHelper;

import java.io.File;

public class ActionsCacheFactory
{
  private static final String ACTIONS_FILE = "src/main/resources/actions.json";

  private static ActionsCacheFactory instance;

  private final ActionsCache actionsCache;
  private final QuickCommandsCache quickCommandsCache;

  private ActionsCacheFactory()
  {
    Root root = GsonHelper.readJSON(new File(ACTIONS_FILE), Root.class);
    actionsCache = new ActionsCache(root);
    quickCommandsCache = new QuickCommandsCache(root);
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

  private ActionsCache getActionsCache()
  {
    return actionsCache;
  }

  Action findAction(String proc) throws UnknownActionException
  {
    return getActionsCache().findAction(proc);
  }

  public boolean isQuickCommand(String cmd)
  {
    return quickCommandsCache.isQuickCommand(cmd);
  }

  public QuickCommand findQuickCommand(String cmd)
  {
    return quickCommandsCache.findQuickCommand(cmd);
  }
}
