package com.github.dagwud.woodlands.game.commands.invocation.plan;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvoker;
import com.github.dagwud.woodlands.game.commands.invocation.DeferredActionInvoker;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvocationPlan
{
  private final List<InvocationPlanStep> invokers;
  private final GameState gameState;
  private Suspension suspension;

  InvocationPlan(GameState gameState)
  {
    this.invokers = new ArrayList<>();
    this.gameState = gameState;
  }

  void add(ActionInvoker actionInvoker)
  {
    InvocationPlanStep step = new InvocationPlanStep(actionInvoker);
    invokers.add(step);
  }

  public List<InvocationPlanStep> getInvokers()
  {
    return Collections.unmodifiableList(invokers);
  }

  public void insertAdditionalInvokers(List<InvocationPlanStep> additional, DeferredActionInvoker insertAfter)
  {
    int addAfterIndex = findIndex(insertAfter) + 1;
    invokers.addAll(addAfterIndex, additional);
  }

  private int findIndex(DeferredActionInvoker find)
  {
    for (int i = 0; i < invokers.size(); i++)
    {
      InvocationPlanStep invoker = invokers.get(i);
      if (invoker.getActionInvoker() == find)
      {
        return i;
      }
    }
    throw new ArrayIndexOutOfBoundsException("Could not find original action");
  }

  public GameState getGameState()
  {
    return gameState;
  }

  public void setSuspended(int suspendedAtStep, InvocationResults suspendedResults)
  {
    suspension = new Suspension(suspendedAtStep, suspendedResults);
  }

  public Suspension getSuspension()
  {
    return suspension;
  }
}
