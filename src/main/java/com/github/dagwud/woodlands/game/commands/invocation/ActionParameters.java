package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.HashMap;
import java.util.Map;

class ActionParameters
{
  private final Map<String, String> values = new HashMap<>();

  ActionParameters(Map<String, String> callParameters)
  {
    for (Map.Entry<String, String> callParameter : callParameters.entrySet())
    {
      values.put(callParameter.getKey(), callParameter.getValue());
    }
  }

  boolean containsParameter(String variableName)
  {
    return values.containsKey(variableName);
  }

  String getParameterValue(String variableName)
  {
    return values.get(variableName);
  }
}
