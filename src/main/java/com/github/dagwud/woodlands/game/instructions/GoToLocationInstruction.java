package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;
import com.github.dagwud.woodlands.gson.game.ParamMappings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    Map<String, String> inputs = new HashMap<>();
    inputs.put("NewLocation", locationName);
    ParamMappings params = new ParamMappings();
    params.mappings.put("NewLocation", locationName);
    CallDetails callDetails = new CallDetails(inputs, params);
    InvocationPlan plan = ActionInvocationPlanner.plan("Goto", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
  }
}
