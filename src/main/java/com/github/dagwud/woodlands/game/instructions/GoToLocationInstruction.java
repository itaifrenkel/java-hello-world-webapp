package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;

public abstract class GoToLocationInstruction extends GameInstruction
{
  private final String locationName;

  GoToLocationInstruction(String locationName)
  {
    this.locationName = locationName;
  }

  @Override
  public void execute(GameState gameState) throws ActionInvocationException
  {
    Variables inputs = new Variables();
    inputs.put("NewLocation", locationName); //todo only one of these (inputs or params) should be necessary

    Variables params = new Variables();
    params.put("NewLocation", locationName); //todo only one of these (inputs or params) should be necessary

    CallDetails callDetails = new CallDetails(inputs, params);
    InvocationPlan plan = ActionInvocationPlanner.plan("Goto", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
  }
}
