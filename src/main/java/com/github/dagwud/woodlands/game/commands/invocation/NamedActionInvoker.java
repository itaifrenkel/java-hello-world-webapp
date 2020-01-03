package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.gson.Action;
import com.github.dagwud.woodlands.gson.ParamMappings;
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
  void verifyParameters(Variables parameters) throws ActionParameterException
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
  String getActionName()
  {
    return action.name;
  }

  @Override
  ActionParameters doInvoke(Variables context, ParamMappings outputMappings) throws ActionInvocationException
  {
    verifyParameters(context);

    System.out.println(action.name + " invoking");
    for (Step step : action.steps)
    {
      invokeStep(step, context);
    }
    return null;
  }

  private void invokeStep(Step step, Variables context) throws ActionInvocationException
  {
    Map<String, String> callParameters = buildParameters(step);
    ParamMappings outputMappings = step.outputMappings == null ? new ParamMappings() : step.outputMappings;
    ActionInvokerDelegate.invoke(step.procName, callParameters, context, outputMappings);
  }

  private Map<String, String> buildParameters(Step step)
  {
    Map<String, String> callParameters = new HashMap<>();
    if (step.paramMappings != null)
    {
      callParameters.putAll(step.paramMappings.mappings);
    }
    return callParameters;
  }
}
