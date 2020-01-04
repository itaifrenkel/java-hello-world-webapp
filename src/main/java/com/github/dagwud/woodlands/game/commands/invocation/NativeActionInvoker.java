package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.natives.NativeAction;
import com.github.dagwud.woodlands.gson.game.ParamMappings;

class NativeActionInvoker extends ActionInvoker
{
  private final NativeAction nativeAction;

  NativeActionInvoker(NativeAction action)
  {
    this.nativeAction = action;
  }

  @Override
  String getActionName()
  {
    return nativeAction.getClass().getSimpleName() + " (native)";
  }

  @Override
  InvocationResults doInvoke(GameState gameState, ParamMappings outputMappings) throws ActionInvocationException
  {
    try
    {
      return nativeAction.invoke(gameState);
    }
    catch (Exception e)
    {
      throw new ActionInvocationException(e);
    }
  }
}
