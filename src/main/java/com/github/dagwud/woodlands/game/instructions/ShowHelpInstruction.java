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

public class ShowHelpInstruction extends GameInstruction
{
  ShowHelpInstruction()
  {
  }

  @Override
  public void execute(GameState gameState) throws IOException
  {
    CallDetails callDetails = new CallDetails(new HashMap<>(), new ParamMappings());
    InvocationPlan plan = ActionInvocationPlanner.plan("ShowHelp", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
  }
}
