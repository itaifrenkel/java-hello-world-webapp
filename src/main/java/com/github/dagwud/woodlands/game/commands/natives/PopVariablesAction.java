package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

@SuppressWarnings("unused") // called at runtime via reflection
public class PopVariablesAction extends NativeAction
{
  public PopVariablesAction()
  {
  }

  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails)
  {
    gameState.getVariables().dropStackFrame();
    return new InvocationResults(new Variables());
  }
}
