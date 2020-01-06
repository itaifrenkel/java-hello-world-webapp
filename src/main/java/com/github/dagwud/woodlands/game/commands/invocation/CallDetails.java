package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.Map;

public class CallDetails
{
  private final Variables callParameters;
  private final Variables outputMappings;

  public CallDetails(Variables callParameters, Variables outputMappings)
  {
    this.callParameters = callParameters;
    this.outputMappings = outputMappings;
  }

  public Variables getCallParameters()
  {
    return callParameters;
  }

  Variables getOutputMappings()
  {
    return outputMappings;
  }
}
