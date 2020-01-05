package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.gson.game.Action;

public class ActionInvokerDelegate
{
  private static final String NATIVE_ACTION_PREFIX = "Native:"; //todo moved

  public static InvocationResults invoke(GameState gameState, String procName) throws ActionInvocationException
  {
    ActionInvoker invoker = createInvoker(procName);
    return invoker.invoke(gameState);
  }

  //todo moved
  private static ActionInvoker createInvoker(String procName) throws ActionInvocationException
  {
    if (isNativeAction(procName))
    {
      String nativeActionName = procName.substring(NATIVE_ACTION_PREFIX.length());
      return NativeActionInvokerFactory.create(nativeActionName, null);
    }

    Action invokedAction = lookupAction(procName);
    return new NamedActionInvoker(invokedAction, null);
  }

  private static boolean isNativeAction(String procName) //todo moved
  {
    return procName.startsWith(NATIVE_ACTION_PREFIX);
  }

  //todo moved
  private static Action lookupAction(String procName) throws ActionInvocationException
  {
    return ActionsCacheFactory.instance().getActions().findAction(procName);
  }

}
