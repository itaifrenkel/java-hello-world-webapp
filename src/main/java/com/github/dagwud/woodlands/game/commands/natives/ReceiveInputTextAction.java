package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReceiveInputTextAction extends NativeAction
{
  @Override
  public InvocationResults invoke(GameState gameState)
  {
    String input = gameState.getVariables().lookupVariableValue("^^input");
    Variables result = new Variables(getClass().getSimpleName(), new HashMap<>());
    result.put("CapturedText", input);
    return new InvocationResults(result);
  }
}
