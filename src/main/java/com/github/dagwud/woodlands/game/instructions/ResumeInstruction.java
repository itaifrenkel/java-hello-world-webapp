package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;

public class ResumeInstruction extends GameInstruction
{
  private final String text;

  ResumeInstruction(String text)
  {
    this.text = text;
  }

  @Override
  public void execute(GameState gameState) throws ActionInvocationException
  {
    gameState.suspended.getGameState().getVariables().setValue("__buffer", text);
    ActionInvocationPlanExecutor.resume(gameState.suspended);
  }
}
