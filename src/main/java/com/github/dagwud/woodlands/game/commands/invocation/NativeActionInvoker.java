package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.natives.NativeAction;

class NativeActionInvoker extends ActionInvoker
{
  private final NativeAction nativeAction;

  NativeActionInvoker(NativeAction action, CallDetails callDetails)
  {
    super(callDetails);
    this.nativeAction = action;
  }

  @Override
  String getActionName()
  {
    return nativeAction.getClass().getSimpleName() + " (native)";
  }

  @Override
  InvocationResults doInvoke(GameState gameState, Variables outputMappings) throws ActionInvocationException
  {
    try
    {
      return nativeAction.invoke(gameState, callDetails);
    }
    catch (Exception e)
    {
      throw new ActionInvocationException(e);
    }
  }
}
