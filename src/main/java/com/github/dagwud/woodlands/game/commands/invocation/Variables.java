package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.HashMap;
import java.util.Map;

public class Variables extends HashMap<String, String>
{
  private final String comment;

  public Variables(String comment, Map<String, String> callParameters)
  {
    super(callParameters);
    this.comment = comment;
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
    for (Map.Entry<String, String> value : entrySet())
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
