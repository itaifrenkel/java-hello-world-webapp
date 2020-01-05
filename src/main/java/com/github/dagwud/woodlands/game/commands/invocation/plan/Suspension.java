package com.github.dagwud.woodlands.game.commands.invocation.plan;

import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;

public class Suspension
{
  private final int suspendedAt;
  private final InvocationResults suspendedResults;

  Suspension(int suspendedAtStep, InvocationResults suspendedResults)
  {
    this.suspendedAt = suspendedAtStep;
    this.suspendedResults = suspendedResults;
  }

  public int getSuspendedAt()
  {
    return suspendedAt;
  }

  public InvocationResults getSuspendedResults()
  {
    return suspendedResults;
  }
}
