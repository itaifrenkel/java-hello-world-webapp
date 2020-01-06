package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;

public class ShowHelpInstruction extends GameInstruction
{
  ShowHelpInstruction()
  {
  }

  @Override
  public void execute(GameState gameState) throws ActionInvocationException
  {
    CallDetails callDetails = new CallDetails(new Variables(), new Variables());
    InvocationPlan plan = ActionInvocationPlanner.plan("ShowHelp", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
  }
}
