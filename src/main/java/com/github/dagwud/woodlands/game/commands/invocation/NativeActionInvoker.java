package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.game.commands.natives.NativeAction;
import com.github.dagwud.woodlands.gson.game.ParamMappings;

import java.io.IOException;

class NativeActionInvoker extends ActionInvoker
{
  private final NativeAction nativeAction;

  NativeActionInvoker(NativeAction action)
  {
    this.nativeAction = action;
  }

  @Override
  void verifyParameters(VariableStack parameters) throws ActionParameterException
  {
    nativeAction.verifyParameters(parameters);
  }

  @Override
  String getActionName()
  {
    return nativeAction.getClass().getSimpleName() + " (native)";
  }

  @Override
  Variables doInvoke(GameState gameState, VariableStack context, ParamMappings outputMappings) throws ActionInvocationException
  {
    nativeAction.verifyParameters(context);
    try
    {
      return nativeAction.invoke(gameState, context);
    }
    catch (Exception e)
    {
      throw new ActionInvocationException(e);
    }
  }
}
