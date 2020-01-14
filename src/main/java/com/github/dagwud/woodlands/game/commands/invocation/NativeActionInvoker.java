package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.natives.NativeAction;

class NativeActionInvoker
{
  private final Variables callDetails;
  private final NativeAction nativeAction;

  NativeActionInvoker(NativeAction action, Variables callDetails)
  {
    this.callDetails = callDetails;
    this.nativeAction = action;
  }

  InvocationResults doInvoke(GameState gameState) throws ActionInvocationException
  {
    try
    {
      InvocationResults results = nativeAction.invoke(gameState, callDetails);
      StepInvoker.mapResults(results.getVariables(), gameState.getVariables());
      return results;
    }
    catch (Exception e)
    {
      throw e instanceof ActionInvocationException ? (ActionInvocationException)e : new ActionInvocationException(e);
    }
  }

  @Override
  public String toString()
  {
    return "NativeActionInvoker{" +
            "nativeAction=" + nativeAction.getClass().getSimpleName() +
            '}';
  }

}
