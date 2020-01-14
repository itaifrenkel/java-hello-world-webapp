package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;

import java.util.Stack;

public class VariableStack
{
  private final Stack<Variables> stack;

  public VariableStack()
  {
    stack = new Stack<>();
    pushNewVariablesStackFrame("<root>", new Variables());
  }

  void pushNewVariablesStackFrame(String stackName, Variables callParameters)
  {
    stack.push(new Variables(stackName, callParameters));
  }

  private String buildContext()
  {
    StringBuilder b = new StringBuilder();
    for (Variables variables : stack)
    {
      if (b.length() != 0)
      {
        b.append(" -> ");
      }
      b.append(variables.getContextName()).append(" (").append(variables.size()).append(")");
    }
    return b.toString();
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
        String value = stackFrame.get(variableName);
        // cases like a=${a} - i.e. it's intended to read from parent frame - don't use this value but keep looking:
         if (value != null && !value.equals(ValueResolver.START_VARIABLE + variableName + ValueResolver.END_VARIABLE))
        {
          return value;
        }
      }
    }
    throw new VariableUndefinedException(variableName, this);
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

  void unsetValue(String valueName)
  {
    if (!stack.get(stack.size() - 1).containsKey(valueName))
    {
      throw new WoodlandsRuntimeException("Value " + valueName + " isn't set");
    }
    stack.get(stack.size() - 1).remove(valueName);
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

  public String pretty()
  {
    StringBuilder b = new StringBuilder();
    b.append("===================\n");
    for (int i = 0; i < stack.size(); i++)
    {
      String s = stack.get(i).pretty(i);
      b.append(s).append("\n");
    }
    b.append("===================");
    return b.toString();
  }

  public boolean hasVariable(String variableName)
  {
    try
    {
      lookupVariableValue(variableName);
      return true;
    }
    catch (VariableUndefinedException e)
    {
      return false;
    }
  }
}
