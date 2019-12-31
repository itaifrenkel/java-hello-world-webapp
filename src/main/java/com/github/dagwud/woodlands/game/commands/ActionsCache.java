package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.InvalidGameDefinition;
import com.github.dagwud.woodlands.gson.Action;
import com.github.dagwud.woodlands.gson.Package;
import com.github.dagwud.woodlands.gson.Root;

import java.util.HashMap;
import java.util.Map;

public class ActionsCache
{
  private final Map<String, Action> actions;

  public ActionsCache(Root root)
  {
    this.actions = new HashMap<String, Action>();
    for (Package aPackage : root.packages)
    {
      for (Action action : aPackage.actions)
      {
        actions.put(buildActionReference(action), action);
      }
    }
  }

  public Action findAction(String actionName) throws InvalidGameDefinition
  {
    Action found = actions.get(actionName);
    if (null == found)
    {
      throw new InvalidGameDefinition("No action named '" + actionName + "' exists");
    }
    return found;
  }

  private String buildActionReference(Action action)
  {
    // maybe we want to include the pacakge name in future?
    return action.name;
  }
}
