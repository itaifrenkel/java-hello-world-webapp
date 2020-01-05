package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.gson.game.ParamMappings;

import java.util.Map;

public class CallDetails
{
  private final Map<String, String> callParameters;
  public final ParamMappings outputMappings;

  public CallDetails(Map<String, String> callParameters, ParamMappings outputMappings)
  {
    this.callParameters = callParameters;
    this.outputMappings = outputMappings;
  }

  public Map<String, String> getCallParameters()
  {
    return callParameters;
  }

  ParamMappings getOutputMappings()
  {
    return outputMappings;
  }
}
