package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.gson.game.Action;

public class ActionInvokerDelegate
{
  private static final String NATIVE_ACTION_PREFIX = "Native:";

  public static InvocationResults invoke(GameState gameState, String procName, CallDetails callDetails) throws ActionInvocationException
  {
    ActionInvoker invoker = createInvoker(procName);
    InvocationResults result = invoker.invoke(gameState, callDetails);
    return result;
  }

  private static ActionInvoker createInvoker(String procName) throws ActionInvocationException
  {
    if (isNativeAction(procName))
    {
      String nativeActionName = procName.substring(NATIVE_ACTION_PREFIX.length());
      return NativeActionInvokerFactory.create(nativeActionName);
    }

    Action invokedAction = lookupAction(procName);
    return new NamedActionInvoker(invokedAction);
  }

  private static boolean isNativeAction(String procName)
  {
    return procName.startsWith(NATIVE_ACTION_PREFIX);
  }

  private static Action lookupAction(String procName) throws ActionInvocationException
  {
    return ActionsCacheFactory.instance().getActions().findAction(procName);
  }

}
