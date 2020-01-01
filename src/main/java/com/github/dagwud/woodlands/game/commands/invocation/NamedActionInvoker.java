package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.gson.Action;
import com.github.dagwud.woodlands.gson.Step;

import java.util.HashMap;
import java.util.Map;

class NamedActionInvoker extends ActionInvoker
{
  private final Action action;

  NamedActionInvoker(Action action)
  {
    this.action = action;
  }

  @Override
  void verifyParameters(ActionParameters parameters) throws ActionParameterException
  {
    if (action.inputs != null)
    {
      for (String input : action.inputs)
      {
        parameters.verifyRequiredParameter(action.name, input);
      }
    }
  }

  @Override
  ActionResults invoke(ActionParameters parameters) throws ActionInvocationException
  {
    verifyParameters(parameters);

    System.out.println(action.name + " invoking");
    for (Step step : action.steps)
    {
      invokeStep(step);
    }
    ActionResults result = new ActionResults();
    System.out.println(action.name + " result: " + result);
    return result;
  }

  private void invokeStep(Step step) throws ActionInvocationException
  {
    Map<String, String> callParameters = buildParameters(step);
    ActionInvokerDelegate.invoke(step.procName, callParameters);
  }

  private Map<String, String> buildParameters(Step step)
  {
    Map<String, String> callParameters = new HashMap<>();
    if (step.paramMappings != null)
    {
      for (Map.Entry<String, String> paramBinding : step.paramMappings.mappings.entrySet())
      {
        String resolvedValue = resolveValue(paramBinding.getValue());
        callParameters.put(paramBinding.getKey(), resolvedValue);
      }
    }
    return callParameters;
  }
}
