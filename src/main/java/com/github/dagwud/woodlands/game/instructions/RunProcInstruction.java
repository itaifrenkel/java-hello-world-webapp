package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.*;

public class RunProcInstruction extends GameInstruction
{
  private final String procName;
  private final Variables callDetails;

  public RunProcInstruction(String procName)
  {
    this(procName, new Variables());
  }

  RunProcInstruction(String procName, Variables callParameters)
  {
    this.procName = procName;
    callDetails = callParameters;
  }

  @Override
  public void execute(GameState gameState) throws ActionInvocationException
  {
    new ActionInvoker(procName, gameState).invokeAction(callDetails);
  }
}
