package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ActionParameters
{
  private final String comment;
  private final Map<String, String> values = new HashMap<>();

  public ActionParameters(String comment, Map<String, String> callParameters)
  {
    this.comment = comment;
    for (Map.Entry<String, String> callParameter : callParameters.entrySet())
    {
      values.put(callParameter.getKey(), callParameter.getValue());
    }
  }

  boolean containsParameter(String variableName)
  {
    return values.containsKey(variableName);
  }

  Map<String, String> getValues()
  {
    return Collections.unmodifiableMap(values);
  }

  String getParameterValue(String variableName)
  {
    return values.get(variableName);
  }

  public void putParameterValue(String variableName, String value)
  {
    values.put(variableName, value);
  }

  @Override
  public String toString()
  {
    return toString(0);
  }

  String toString(int indent)
  {
    StringBuilder b = new StringBuilder();
    b.append(space(indent)).append("| ").append(comment).append("\n");
    b.append(space(indent)).append("| Variable              | Value   ").append("\n");
    for (Map.Entry<String, String> value : values.entrySet())
    {
      b.append(space(indent)).append("| ").append(value.getKey());
      b.append(space(indent)).append(space(value.getKey(), 23 - value.getKey().length()));
      b.append(space(indent)).append("| ").append(value.getValue()).append("\n");
    }
    b.append(space(indent)).append("----------------------------------");
    return b.toString();
  }

  private String space(int indent)
  {
    return space("", indent);
  }

  private String space(String s, int spaces)
  {
    StringBuilder b= new StringBuilder();
    for (int i = s.length(); i < spaces; i++)
    {
      b.append("  ");
    }
    return b.toString();
  }
}
