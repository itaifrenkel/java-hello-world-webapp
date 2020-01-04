package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.gson.game.Action;
import com.github.dagwud.woodlands.gson.game.ParamMappings;

import java.util.HashMap;
import java.util.Map;

public class ActionInvokerDelegate
{
  private static final String NATIVE_ACTION_PREFIX = "Native:";

  public static void invoke(GameState gameState, String procName, Map<String, String> callParameters, ParamMappings outputMappings) throws ActionInvocationException
  {
    ActionInvoker invoker = createInvoker(procName);
    invoker.invoke(gameState, callParameters, outputMappings); //todo
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
