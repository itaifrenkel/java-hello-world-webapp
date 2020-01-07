package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;

public class RunProcInstruction extends GameInstruction
{
  private final String procName;
  private final CallDetails callDetails;

  RunProcInstruction(String procName)
  {
    this.procName = procName;
    callDetails = new CallDetails(new Variables(), new Variables());
  }

  @Override
  public void execute(GameState gameState) throws ActionInvocationException
  {
    InvocationPlan plan = ActionInvocationPlanner.plan(procName, gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
  }
}
