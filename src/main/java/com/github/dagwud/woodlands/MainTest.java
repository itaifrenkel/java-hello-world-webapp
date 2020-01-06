package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;

public class MainTest
{
  public static void main(String[] args) throws ActionInvocationException
  {
    GameState gameState = GameStatesRegistry.lookup(-1);
    gameState.getVariables().setValue("chatId", "-1");
    CallDetails callDetails = new CallDetails(new Variables(), new Variables());

    InvocationPlan plan = ActionInvocationPlanner.plan("PlayerSetup", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
    // suspends to ask for player text:

    plan.getGameState().getVariables().setValue("__buffer", "helloooo");
    ActionInvocationPlanExecutor.resume(plan);

    plan.getGameState().getVariables().setValue("__buffer", "Druid");
    ActionInvocationPlanExecutor.resume(plan);
  }

}
