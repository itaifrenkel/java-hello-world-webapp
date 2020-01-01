package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.NativeAction;

class NativeActionInvokerFactory
{
  private NativeActionInvokerFactory()
  {
  }

  static NativeActionInvoker create(String nativeProcName) throws ActionInvocationException
  {
    NativeAction action = NativeActionResolver.lookupNativeAction(nativeProcName);
    return new NativeActionInvoker(action);
  }
}
