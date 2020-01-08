package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.plan.ActionInvocationPlanner;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MainTest
{
  @Test
  public void testPlayerSetup() throws ActionInvocationException
  {
    GameState gameState = GameStatesRegistry.lookup(-1);
    gameState.getVariables().setValue("chatId", "-1");
    CallDetails callDetails = new CallDetails(new Variables(), new Variables());

    InvocationPlan plan = ActionInvocationPlanner.plan("PlayerSetup", gameState, callDetails);
    ActionInvocationPlanExecutor.execute(plan);
    // suspends to ask for player name
    plan.getGameState().getVariables().setValue("__buffer", "helloooo");
    ActionInvocationPlanExecutor.resume(plan);
    // suspends to ask for player class
    plan.getGameState().getVariables().setValue("__buffer", "Druid");
    ActionInvocationPlanExecutor.resume(plan);

    assertEquals("helloooo", gameState.getVariables().lookupVariableValue("Player.Name"));
    assertEquals("Druid", gameState.getVariables().lookupVariableValue("Player.Class"));
    assertEquals("1", gameState.getVariables().lookupVariableValue("Player.Level"));
    assertEquals("80", gameState.getVariables().lookupVariableValue("Player.HP"));
    assertEquals("100", gameState.getVariables().lookupVariableValue("Player.MaxHP"));
    assertEquals("2", gameState.getVariables().lookupVariableValue("Player.Mana"));
    assertEquals("3", gameState.getVariables().lookupVariableValue("Player.MaxMana"));
  }

}
