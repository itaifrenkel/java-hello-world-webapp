package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.invocation.ActionInvokerDelegate;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;

public class GameState
{
  private static GameState instance;
  private ActionInvokerDelegate actionInvokerDelegate;

  private GameState()
  {
    actionInvokerDelegate = new ActionInvokerDelegate();
  }

  public static GameState instance()
  {
    if (null == instance)
    {
      createInstance();
    }
    return instance;
  }

  private synchronized static void createInstance()
  {
    if (null != instance)
    {
      return;
    }
    instance = new GameState();
  }

  public ActionInvokerDelegate getActionInvokerDelegate()
  {
    return actionInvokerDelegate;
  }
}
