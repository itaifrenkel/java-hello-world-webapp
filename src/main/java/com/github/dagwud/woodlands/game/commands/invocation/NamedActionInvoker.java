package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.InvalidGameDefinition;
import com.github.dagwud.woodlands.gson.Action;
import com.github.dagwud.woodlands.gson.Step;

import java.util.HashMap;
import java.util.Map;

class NamedActionInvoker extends ActionInvoker
{
  private final Action action;

  NamedActionInvoker(Action action, Map<String, String> callParameters)
  {
    super(callParameters);
    this.action = action;
  }

  @Override
  ActionResults invoke() throws ActionInvocationException
  {
    System.out.println("INVOKING: " + action.name);
    for (Step step : action.steps)
    {
      invokeStep(step);
    }
    return new ActionResults();
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
