package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlanStep;
import com.github.dagwud.woodlands.game.commands.invocation.plan.Suspension;

import java.util.List;

public abstract class ActionInvocationPlanExecutor
{
  private ActionInvocationPlanExecutor()
  {
  }

  public static void execute(InvocationPlan plan) throws ActionInvocationException
  {
    execute(plan, 0);
  }

  public static void resume(InvocationPlan plan) throws ActionInvocationException
  {
    System.out.println("Resuming: \n" + plan.getGameState().getVariables());
    Suspension suspension = plan.getSuspension();
    InvocationPlanStep suspendedStep = plan.getInvokers().get(suspension.getSuspendedAt());

    suspendedStep.getActionInvoker().complete(plan.getGameState(),
            suspension.getSuspendedResults(),
            suspendedStep.getActionInvoker().callDetails.getOutputMappings());
    execute(plan, suspension.getSuspendedAt() + 1);
  }

  private static void execute(InvocationPlan plan, int startAt) throws ActionInvocationException
  {
    List<InvocationPlanStep> invokers = plan.getInvokers();
    for (int i = startAt; i < invokers.size(); i++)
    {
      InvocationPlanStep invoker = invokers.get(i);
      try
      {
        InvocationResults result = invoker.getActionInvoker().invoke(plan.getGameState());
        if (result.getReturnMode() == ReturnMode.SUSPEND)
        {
          plan.setSuspended(i, result);
          System.out.println("-----SUSPENDED-----");
          return;
        }
      }
      catch (Exception e)
      {
        throw new ActionInvocationException("Error while invoking plan step " + invoker.getActionInvoker().getActionName(), e);
      }
    }
  }
}
