package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.gson.game.Action;
import com.github.dagwud.woodlands.gson.game.ParamMappings;
import com.github.dagwud.woodlands.gson.game.Step;

import java.util.HashMap;
import java.util.Map;

public class NamedActionInvoker extends ActionInvoker
{
  private final Action action;

  public NamedActionInvoker(Action action, CallDetails callDetails)
  {
    super(callDetails);
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
      InvocationResults result = invokeStep(gameState, step);
      if (result.getReturnMode() != ReturnMode.CONTINUE)
      {
        return result;
      }
    }
    return new InvocationResults(null);
  }

  private InvocationResults invokeStep(GameState gameState, Step step) throws ActionInvocationException
  {
    Map<String, String> callParameters = buildParameters(step);
    ParamMappings outputMappings = step.outputMappings == null ? new ParamMappings() : step.outputMappings;
    CallDetails callDetails = new CallDetails(callParameters, outputMappings);
    return ActionInvokerDelegate.invoke(gameState, step.procName);
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
