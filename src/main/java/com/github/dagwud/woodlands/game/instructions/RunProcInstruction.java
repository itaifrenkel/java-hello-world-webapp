package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.*;

public class RunProcInstruction extends GameInstruction
{
  private final String procName;
  private final CallDetails callDetails;

  RunProcInstruction(String procName)
  {
    this(procName, new Variables(), new Variables());
  }

  RunProcInstruction(String procName, Variables callParameters, Variables outputMappings)
  {
    this.procName = procName;
    callDetails = new CallDetails(callParameters, outputMappings);
  }

  @Override
  public void execute(GameState gameState) throws ActionInvocationException
  {
    new ActionInvoker2(procName, gameState).invokeAction(callDetails.getCallParameters());
  }
}
