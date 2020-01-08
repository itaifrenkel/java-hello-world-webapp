package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlanStep;

import java.util.List;

public class DeferredActionInvoker extends ActionInvoker
{
  private final String procName;
  private final InvocationPlan invokers;
  private final DeferredActionInvoker addAfter;

  public DeferredActionInvoker(String procName, CallDetails callDetails, InvocationPlan invokers)
  {
    super(callDetails);
    this.procName = procName;
    this.invokers = invokers;
    this.addAfter = this;
  }

  @Override
  String getActionName()
  {
    return "(deferred) " + procName;
  }

  @Override
  InvocationResults doInvoke(GameState gameState, Variables outputMappings) throws ActionInvocationException
  {
    String proc = ValueResolver.resolve(procName, gameState.getVariables());
    InvocationPlan subplan = ActionInvocationPlanner.plan(proc, gameState, callDetails);

    invokers.insertAdditionalInvokers(subplan.getInvokers(), addAfter);
    return new InvocationResults(new Variables());
  }

}
