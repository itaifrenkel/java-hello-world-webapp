package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.invocation.ActionInvoker2;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;

public class GameState
{
  private final VariableStack variables;
  public ActionInvoker2 suspended2;

  GameState()
  {
    variables = new VariableStack();
  }

  public VariableStack getVariables()
  {
    return variables;
  }

}
