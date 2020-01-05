package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.NativeAction;

public class NativeActionInvokerFactory
{
  private NativeActionInvokerFactory()
  {
  }

  public static NativeActionInvoker create(String nativeProcName, CallDetails callDetails) throws ActionInvocationException
  {
    NativeAction action = NativeActionResolver.lookupNativeAction(nativeProcName);
    return new NativeActionInvoker(action, callDetails);
  }
}
