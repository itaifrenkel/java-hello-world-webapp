package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.Map;

public class CallDetails
{
  private final Map<String, String> callParameters;
  private final Variables outputMappings;

  public CallDetails(Map<String, String> callParameters, Variables outputMappings)
  {
    this.callParameters = callParameters;
    this.outputMappings = outputMappings;
  }

  public Map<String, String> getCallParameters()
  {
    return callParameters;
  }

  Variables getOutputMappings()
  {
    return outputMappings;
  }
}
