package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.gson.game.Action;
import com.github.dagwud.woodlands.gson.game.Step;

public class NamedActionInvoker extends ActionInvoker
{
  private final Action action;

  public NamedActionInvoker(Action action, CallDetails callDetails)
  {
    super(callDetails);
    this.action = action;
  }

  @Override
  String getActionName()
  {
    return action.name;
  }

  @Override
  InvocationResults doInvoke(GameState gameState, Variables outputMappings) throws ActionInvocationException
  {
    for (Step step : action.steps)
    {
      InvocationResults result = invokeStep(gameState, step);
      if (result.getReturnMode() != ReturnMode.CONTINUE)
      {
        return result;
      }
    }
    return new InvocationResults(null);
  }

  private InvocationResults invokeStep(GameState gameState, Step step) throws ActionInvocationException
  {
    String proc = ValueResolver.resolve(step.procName, gameState.getVariables());
    return ActionInvokerDelegate.invoke(gameState, proc);
  }

}
