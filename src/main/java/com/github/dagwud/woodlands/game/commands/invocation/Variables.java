package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.game.commands.natives.MissingRequiredParameterException;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Variables
{
  private final Stack<ActionParameters> stack;

  Variables()
  {
    stack = new Stack<>();
    stack.push(new ActionParameters(new HashMap<String, String>(0)));
  }

  void pushNewVariablesStackFrame(Map<String, String> callParameters)
  {
    stack.push(new ActionParameters(callParameters));
  }

  void dropStackFrame()
  {
    stack.pop();
  }

  public void verifyRequiredParameter(String actionName, String requiredParameterName) throws ActionParameterException
  {
    if (lookupVariable(requiredParameterName) == null)
    {
      throw new MissingRequiredParameterException(actionName, requiredParameterName);
    }
  }

  public String lookupVariableValue(String varName)
  {
    return ValueResolver.resolve(ValueResolver.START_VARIABLE + varName + ValueResolver.END_VARIABLE, this);
  }

  String lookupVariable(String variableName)
  {
    for (int i = stack.size() - 1; i >= 0; i--)
    {
      ActionParameters stackFrame = stack.get(i);
      if (stackFrame.containsParameter(variableName))
      {
        return stackFrame.getParameterValue(variableName);
      }
    }
    return null;
  }
}
