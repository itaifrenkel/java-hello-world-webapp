package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.game.commands.natives.MissingRequiredParameterException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class VariableStack
{
  private final Stack<Variables> stack;

  public VariableStack()
  {
    stack = new Stack<>();
    stack.push(new Variables("root", new HashMap<String, String>(0)));
  }

  void pushNewVariablesStackFrame(String name, Map<String, String> callParameters)
  {
    stack.push(new Variables(name, callParameters));
  }

  void dropStackFrame()
  {
    stack.pop();
  }

  public void verifyRequiredParameter(String actionName, String requiredParameterName) throws ActionParameterException
  {
    if (!hasVariable(requiredParameterName))
    {
      throw new MissingRequiredParameterException(actionName, requiredParameterName);
    }
  }

  private boolean hasVariable(String requiredParameterName)
  {
    return lookupVariable(requiredParameterName) != null;
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
    throw new VariableUndefinedException(variableName);
  }

  public void setValue(String variableName, String value)
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
    stack.get(stack.size() - 1).put(variableName, value);
  }

  static boolean isGlobalVariable(String variableName)
  {
    return variableName.startsWith("__");
  }

  private void setGlobalValue(String variableName, String value)
  {
    stack.get(0).put(variableName, value);
  }

  @Override
  public String toString()
  {
    StringBuilder b = new StringBuilder();
    b.append("===================\n");
    for (int i = 0; i < stack.size(); i++)
//    for (int i = stack.size() - 1; i >= 0; i--)
    {
      String s = stack.get(i).toString(i);
//      String s = stack.get(i).toString(stack.size() - i);
      b.append(s).append("\n");
    }
    b.append("===================");
    return b.toString();
  }
}
