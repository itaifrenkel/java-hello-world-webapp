package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.NativeAction;

class NativeActionResolver
{
  private static final String NATIVE_ACTIONS_PACKAGE = "com.github.dagwud.woodlands.game.commands.natives";
  private static final String NATIVE_ACTIONS_SUFFIX = "Action";

  static NativeAction lookupNativeAction(String nativeProcName) throws ActionInvocationException
  {
    Class<? extends NativeAction> actionClass = lookupNativeActionClass(nativeProcName);
    return instantiateNativeAction(actionClass);
  }

  private static NativeAction instantiateNativeAction(Class<? extends NativeAction> actionClass) throws ActionInvocationException
  {
    try
    {
      return actionClass.newInstance();
    }
    catch (InstantiationException | IllegalAccessException e)
    {
      throw new ActionInvocationException("Unable to execute native action " + actionClass.getCanonicalName(), e);
    }
  }

  private static Class<? extends NativeAction> lookupNativeActionClass(String nativeProcName) throws UnknownNativeActionException
  {
    Class<?> actionClass;
    try
    {
      actionClass = Class.forName(NATIVE_ACTIONS_PACKAGE + "." + nativeProcName + NATIVE_ACTIONS_SUFFIX);
    }
    catch (ClassNotFoundException e)
    {
      throw new UnknownNativeActionException("Native action \"" + nativeProcName + "\" does not exist");
    }

    if (NativeAction.class.isAssignableFrom(actionClass))
    {
      return (Class<? extends NativeAction>) actionClass;
    }
    throw new UnknownNativeActionException("Native action \"" + nativeProcName + "\" is misconfigured");
  }
}
