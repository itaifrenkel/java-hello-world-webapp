package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.invocation.ActionInvoker;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;

public class GameState
{
  private static GameState instance;
  private final VariableStack variables;
  private SuspendedInvocation suspended;

  GameState()
  {
    variables = new VariableStack();
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

  public VariableStack getVariables()
  {
    return variables;
  }

  public void setSuspendedInvocation(ActionInvoker actionInvoker, GameState gameState, CallDetails callDetails)
  {
    suspended = new SuspendedInvocation(actionInvoker, gameState, callDetails);
  }

  public SuspendedInvocation getSuspendedInvocation()
  {
    return suspended;
  }
}
