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
  ActionResults invoke(ActionCallContext context) throws ActionInvocationException
  {
    verifyParameters(context.getCallParameters());

    System.out.println(action.name + " invoking");
    for (Step step : action.steps)
    {
      invokeStep(step, context);
    }
    ActionResults result = new ActionResults();
    System.out.println(action.name + " result: " + result);
    return result;
  }

  private void invokeStep(Step step, ActionCallContext actionCallContext) throws ActionInvocationException
  {
    Map<String, String> callParameters = buildParameters(step);
    actionCallContext.getCallParameters().pushNewVariablesStackFrame(callParameters);
    ActionInvokerDelegate.invoke(step.procName, actionCallContext);
    actionCallContext.getCallParameters().dropStackFrame();
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
