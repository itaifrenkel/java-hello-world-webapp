package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.NativeAction;

import java.util.Map;

class NativeActionInvokerFactory
{
  private NativeActionInvokerFactory()
  {
  }

  static NativeActionInvoker create(String nativeProcName, Map<String, String> callParameters) throws ActionInvocationException
  {
    NativeAction action = NativeActionResolver.lookupNativeAction(nativeProcName);
    return new NativeActionInvoker(action, callParameters);
  }
}
