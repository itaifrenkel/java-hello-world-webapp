package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.gson.game.Action;
import com.github.dagwud.woodlands.gson.game.Package;
import com.github.dagwud.woodlands.gson.game.Root;

import java.util.HashMap;
import java.util.Map;

public class ActionsCache
{
  private final Map<String, Action> actions;

  public ActionsCache(Root root)
  {
    this.actions = new HashMap<>();
    for (Package aPackage : root.packages)
    {
      for (Action action : aPackage.actions)
      {
        actions.put(buildActionReference(action), action);
      }
    }
  }

  public Action findAction(String actionName) throws UnknownActionException
  {
    Action found = actions.get(actionName);
    if (null == found)
    {
      throw new UnknownActionException("No action named '" + actionName + "' exists");
    }
    return found;
  }

  private String buildActionReference(Action action)
  {
    // maybe we want to include the pacakge name in future?
    return action.name;
  }
}
