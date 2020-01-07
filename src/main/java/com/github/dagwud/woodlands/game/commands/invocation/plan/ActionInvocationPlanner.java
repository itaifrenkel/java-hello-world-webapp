package com.github.dagwud.woodlands.game.commands.invocation.plan;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.*;
import com.github.dagwud.woodlands.gson.game.Action;
import com.github.dagwud.woodlands.gson.game.Step;

import java.math.BigDecimal;

public abstract class ActionInvocationPlanner
{
  private static final String NATIVE_ACTION_PREFIX = "Native:";

  private ActionInvocationPlanner()
  {
  }

  public static InvocationPlan plan(String initialProcName, GameState gameState, CallDetails callDetails) throws ActionInvocationException
  {
    InvocationPlan plan = new InvocationPlan(gameState);
    addInvokers(initialProcName, callDetails, plan);
    return plan;
  }

  private static void addInvokers(String procName, CallDetails callDetails, InvocationPlan invokers) throws ActionInvocationException
  {
    if (procName.contains(ValueResolver.START_VARIABLE))
    {
      invokers.add(new DeferredActionInvoker(procName, callDetails, invokers));
      return;
    }

    if (isNativeAction(procName))
    {
      String nativeActionName = procName.substring(NATIVE_ACTION_PREFIX.length());
      ActionInvoker invoker = NativeActionInvokerFactory.create(nativeActionName, callDetails);
      invokers.add(invoker);
    }
    else
    {
      Action action = lookupAction(procName);
      addInvokers(action, callDetails, invokers);
    }
  }

  private static void addInvokers(Action action, CallDetails callDetails, InvocationPlan invokers) throws ActionInvocationException
  {
    invokers.add(NativeActionInvokerFactory.create("PushVariables", callDetails));
    for (Step step : action.steps)
    {
      Variables callParameters = (step.paramMappings == null ? new Variables() : step.paramMappings);
      CallDetails empty = new CallDetails(callParameters, new Variables());
      invokers.add(NativeActionInvokerFactory.create("PushVariables", empty));

      addInvokers(step, invokers);

      invokers.add(NativeActionInvokerFactory.create("PopVariables", empty));
    }
    invokers.add(NativeActionInvokerFactory.create("PopVariables", callDetails));
  }

  private static void addInvokers(Step step, InvocationPlan invokers) throws ActionInvocationException
  {
    BigDecimal chance = step.determineChanceRatio();
    boolean shouldRun = true;
    if (!chance.equals(BigDecimal.ONE))
    {
      BigDecimal random = new BigDecimal(Math.random());
      shouldRun = chance.compareTo(random) > 0;
      System.out.println("RANDOM CHECK: " + random + " vs " + chance + ": " + shouldRun);
    }

    Step stepToInvoke = null;
    if (shouldRun)
    {
      stepToInvoke = step;
    }
    else if (step.ifFalse != null)
    {
      stepToInvoke = step.ifFalse;
    }

    if (stepToInvoke != null)
    {
      addInvokers(stepToInvoke.procName, createCallDetails(stepToInvoke), invokers);
    }
  }

  private static CallDetails createCallDetails(Step step)
  {
    Variables outputMappings = step.outputMappings == null ? new Variables() : step.outputMappings;
    Variables callParameters = (step.paramMappings == null ? new Variables() : step.paramMappings);
    return new CallDetails(callParameters, outputMappings);
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
