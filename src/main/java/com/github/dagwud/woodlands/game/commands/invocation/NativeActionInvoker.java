package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.game.commands.natives.NativeAction;

class NativeActionInvoker extends ActionInvoker
{
  private final NativeAction nativeAction;

  NativeActionInvoker(NativeAction action)
  {
    this.nativeAction = action;
  }

  @Override
  void verifyParameters(ActionParameters parameters) throws ActionParameterException
  {
    nativeAction.verifyParameters(parameters);
  }

  @Override
  ActionResults invoke(ActionParameters parameters) throws ActionParameterException
  {
    System.out.println(nativeAction.getClass().getSimpleName() + " invoking (native)");
    nativeAction.verifyParameters(parameters);
    ActionResults results = nativeAction.invoke(parameters);
    System.out.println(nativeAction.getClass().getSimpleName() + " result: " + results);
    return results;
  }
}
