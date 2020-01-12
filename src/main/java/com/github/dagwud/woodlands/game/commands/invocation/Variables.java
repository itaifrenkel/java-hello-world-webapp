package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.HashMap;
import java.util.Map;

public class Variables extends HashMap<String, String>
{
  private String contextName;

  public Variables()
  {
    super();
    contextName = "(anon)";
  }

  public Variables(String name, Map<String, String> callParameters)
  {
    super(callParameters == null ? new HashMap<>(0) : callParameters);
    contextName = (name == null) ? "(anon)" : name;
  }

  public Variables(String contextName)
  {
    this.contextName = contextName;
  }

  public String pretty()
  {
    return pretty(0);
  }

  String pretty(int indent)
  {
    StringBuilder b = new StringBuilder();
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

  @Override
  public String toString()
  {
    return "Variables{" +
            "contextName='" + contextName + '\'' +
            '}';
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

  @Override
  public String put(String key, String value)
  {
    //todo inline
    return super.put(key, value);
  }

  String getContextName()
  {
    return contextName;
  }
}
