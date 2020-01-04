package com.github.dagwud.woodlands;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvokerDelegate;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.gson.game.ParamMappings;

import java.util.HashMap;

public class MainTest
{
  public static void main(String[] args) throws ActionInvocationException
  {
    GameState gameState = GameStatesRegistry.lookup(-1);
    gameState.getVariables().setValue("chatId", "-1");
    CallDetails callDetails = new CallDetails(new HashMap<>(), new ParamMappings());
    ActionInvokerDelegate.invoke(gameState, "PlayerSetup", callDetails);

    // suspends to ask for player text:
    gameState.getVariables().setValue("^^input", "hellloooo");
    ActionInvokerDelegate.invoke(gameState, "Native:ReceiveInputText", new CallDetails(new HashMap<>(), new ParamMappings()));
  }

}
