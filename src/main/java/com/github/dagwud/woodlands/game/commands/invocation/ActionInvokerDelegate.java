package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.gson.Action;
import com.github.dagwud.woodlands.gson.ParamMappings;
import com.github.dagwud.woodlands.gson.Step;

import java.util.HashMap;
import java.util.Map;

public class ActionInvokerDelegate
{
  private static final String NATIVE_ACTION_PREFIX = "Native:";

  public static void invoke(String procName) throws ActionInvocationException
  {
    invoke(procName, new HashMap<String, String>(0), new ActionCallContext(), new ParamMappings());
  }

  static void invoke(String procName, Map<String, String> callParameters, ActionCallContext context, ParamMappings outputMappings) throws ActionInvocationException
  {
    ActionInvoker invoker = createInvoker(procName);
    invoker.invoke(context, callParameters, outputMappings); //todo
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
