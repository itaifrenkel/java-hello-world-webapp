package com.github.dagwud.woodlands.game.commands.invocation.plan;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvoker;
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
