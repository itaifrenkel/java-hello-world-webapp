package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;
import com.github.dagwud.woodlands.game.commands.invocation.plan.InvocationPlan;

public class GameState
{
  private final VariableStack variables;
  public InvocationPlan suspended;

  GameState()
  {
    variables = new VariableStack();
  }

  public VariableStack getVariables()
  {
    return variables;
  }

}
