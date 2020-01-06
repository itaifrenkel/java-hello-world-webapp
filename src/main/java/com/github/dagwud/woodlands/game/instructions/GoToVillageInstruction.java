package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvokerDelegate;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;
import com.github.dagwud.woodlands.gson.game.ParamMappings;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class GoToVillage extends GameInstruction
{
  ShowHelpInstruction()
  {
  }

  @Override
  public void execute(GameState gameState) throws ActionInvocationException, IOException
  {
    Map<String, String> inputs = new HashMap<>();
    inputs.put("Location", "The Village");
    CallDetails callDetails = new CallDetails(inputs, new ParamMappings());
    InvocationPlan plan = ActionInvocationPlanner.plan("GoTo", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
  }
}
