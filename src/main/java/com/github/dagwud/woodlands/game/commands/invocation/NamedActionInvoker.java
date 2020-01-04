package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.gson.game.Action;
import com.github.dagwud.woodlands.gson.game.ParamMappings;
import com.github.dagwud.woodlands.gson.game.Step;

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
  String getActionName()
  {
    return action.name;
  }

  @Override
  InvocationResults doInvoke(GameState gameState, ParamMappings outputMappings) throws ActionInvocationException
  {
    System.out.println(action.name + " invoking");
    for (Step step : action.steps)
    {
      invokeStep(gameState, step);
    }
    return new InvocationResults(null);
  }

  private void invokeStep(GameState gameState, Step step) throws ActionInvocationException
  {
    Map<String, String> callParameters = buildParameters(step);
    ParamMappings outputMappings = step.outputMappings == null ? new ParamMappings() : step.outputMappings;
    CallDetails callDetails = new CallDetails(callParameters, outputMappings);
    ActionInvokerDelegate.invoke(gameState, step.procName, callDetails);
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
