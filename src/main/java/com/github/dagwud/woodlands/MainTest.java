package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;
import com.github.dagwud.woodlands.gson.game.ParamMappings;

import java.util.HashMap;

public class MainTest
{
  public static void main(String[] args) throws ActionInvocationException
  {
    GameState gameState = GameStatesRegistry.lookup(-1);
    gameState.getVariables().setValue("chatId", "-1");
    CallDetails callDetails = new CallDetails(new HashMap<>(), new ParamMappings());

    InvocationPlan plan = ActionInvocationPlanner.plan("PlayerSetup", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
    // suspends to ask for player text:

    Variables captured1 = new Variables("captured-text", new HashMap<>(1));
    plan.getGameState().getVariables().setValue("__buffer", "helloooo");
    ActionInvocationPlanExecutor.resume(plan);

    Variables captured2 = new Variables("captured-text", new HashMap<>(1));
    plan.getGameState().getVariables().setValue("__buffer", "Druid");
    ActionInvocationPlanExecutor.resume(plan);
  }

}
