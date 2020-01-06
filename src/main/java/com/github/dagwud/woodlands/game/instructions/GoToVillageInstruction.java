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

public class GoToVillageInstruction extends GameInstruction
{
  @Override
  public void execute(GameState gameState) throws ActionInvocationException, IOException
  {
    Map<String, String> params = new HashMap<>();
    params.put("Location", "The Village");
    CallDetails callDetails = new CallDetails(new HashMap<>(), params);
    InvocationPlan plan = ActionInvocationPlanner.plan("Goto", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
  }
}
