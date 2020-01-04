package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvokerDelegate;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;
import com.github.dagwud.woodlands.gson.game.ParamMappings;

import java.util.HashMap;

public class MainTest
{
  public static void main(String[] args) throws ActionInvocationException
  {
    VariableStack variables = new VariableStack();
    variables.setValue("chatId", "-1");
    GameState gameState = GameStatesRegistry.lookup(-1);
    ActionInvokerDelegate.invoke(gameState, "PlayerSetup", new HashMap<>(), variables, new ParamMappings());
  }

}
