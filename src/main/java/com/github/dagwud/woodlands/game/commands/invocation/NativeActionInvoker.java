package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.NativeAction;

import java.util.Map;

class NativeActionInvoker extends ActionInvoker
{
  private final NativeAction nativeAction;

  NativeActionInvoker(NativeAction action, Map<String, String> callParameters)
  {
    super(callParameters);
    this.nativeAction = action;
  }

  @Override
  ActionResults invoke()
  {
    return nativeAction.invoke();
  }
}
