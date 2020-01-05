package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class VariableStack
{
  private final Stack<Variables> stack;

  public VariableStack()
  {
    stack = new Stack<>();
    stack.push(new Variables("root", new HashMap<>(0)));
  }

  public void pushNewVariablesStackFrame(String name, Map<String, String> callParameters)
  {
    stack.push(new Variables(name, callParameters));
  }

  public void dropStackFrame()
  {
    stack.pop();
  }

  public String lookupVariableValue(String varName)
  {
    return ValueResolver.resolve(ValueResolver.START_VARIABLE + varName + ValueResolver.END_VARIABLE, this);
  }

  String lookupVariable(String variableName)
  {
    for (int i = stack.size() - 1; i >= 0; i--)
    {
      Variables stackFrame = stack.get(i);
      if (stackFrame.containsKey(variableName))
      {
        return stackFrame.get(variableName);
      }
    }
    System.err.println("Not found '" + variableName + "': \n" + this);
    throw new VariableUndefinedException(variableName);
  }

  public void setValue(String variableName, String value)
  {
    setValue(variableName, value, 0);
  }

  void setValue(String variableName, String value, int offset)
  {
    if (isGlobalVariable(variableName))
    {
      setGlobalValue(variableName, value);
      return;
    }

    for (Variables variables : stack)
    {
      if (variables.containsKey(variableName))
      {
        variables.put(variableName, value);
        return;
      }
    }
    stack.get(stack.size() - 1 - (-offset)).put(variableName, value);
  }

  static boolean isGlobalVariable(String variableName)
  {
    return variableName.startsWith("__");
  }

  private void setGlobalValue(String variableName, String value)
  {
    variableName = variableName.substring("__".length());
    stack.get(0).put(variableName, value);
  }

  @Override
  public String toString()
  {
    StringBuilder b = new StringBuilder();
    b.append("===================\n");
    for (int i = 0; i < stack.size(); i++)
    {
      String s = stack.get(i).toString(i);
      b.append(s).append("\n");
    }
    b.append("===================");
    return b.toString();
  }
}
