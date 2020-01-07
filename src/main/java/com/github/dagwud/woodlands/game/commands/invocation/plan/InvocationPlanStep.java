package com.github.dagwud.woodlands.game.commands.invocation.plan;

import com.github.dagwud.woodlands.game.commands.invocation.ActionInvoker;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;

public class InvocationPlanStep
{
  private final ActionInvoker invoker;

  InvocationPlanStep(ActionInvoker actionInvoker)
  {
    this.invoker = actionInvoker;
  }

  public ActionInvoker getActionInvoker()
  {
    return invoker;
  }

  @Override
  public String toString()
  {
    return "InvocationPlanStep{" +
            getActionInvoker().toString() +
            "}";
  }
}
