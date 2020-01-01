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
  ActionResults invoke(ActionCallContext context) throws ActionParameterException
  {
    System.out.println(nativeAction.getClass().getSimpleName() + " invoking (native)");
    nativeAction.verifyParameters(context.getCallParameters());
    ActionResults results = nativeAction.invoke(context);
    System.out.println(nativeAction.getClass().getSimpleName() + " result: " + results);
    return results;
  }
}
