package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.gson.game.Step;

import java.util.Map;

public class StepInvoker
{
  private final Step step;
  private final GameState gameState;
  private ActionInvoker invoker = null;
  private Variables boundParams = null;

  StepInvoker(Step step, GameState gameState)
  {
    this.step = step;
    this.gameState = gameState;
  }

  public InvocationResults invoke() throws ActionInvocationException
  {
    if (invoker == null)
    {
      bindParameters();
    }
    InvocationResults results = invokeStep();
    if (isComplete() && results.getReturnMode() != ReturnMode.SUSPEND)
    {
      unbindParameters(boundParams);
    }
    mapResults(results.getVariables(), gameState.getVariables());
    return results;
  }

  private Variables bindParameters()
  {
    Variables parameters = new Variables();
    boundParams = new Variables();
    if (step.paramMappings != null)
    {
      for (Map.Entry<String, String> paramMapping : step.paramMappings.entrySet())
      {
        String bindTo = paramMapping.getKey();
        String valueExpr = paramMapping.getValue();
        String value = ValueResolver.resolve(valueExpr, gameState.getVariables());
        parameters.put(bindTo, value);
      }
    }
    for (Map.Entry<String, String> params : parameters.entrySet())
    {
      if (!gameState.getVariables().hasVariable(params.getKey()))
      {
        boundParams.put(params.getKey(), params.getValue());
      }
      gameState.getVariables().setValue(params.getKey(), params.getValue());
    }
    return parameters;
  }

  private InvocationResults invokeStep() throws ActionInvocationException
  {
    if (invoker == null)
    {
      invoker = new ActionInvoker(step.procName, gameState);
    }
    return invoker.invokeNext();
  }

  boolean isComplete()
  {
    return !invoker.hasNext();
  }

  static void mapResults(Variables results, VariableStack callContext)
  {
    for (Map.Entry<String, String> result : results.entrySet())
    {
      callContext.setValue(result.getKey(), result.getValue(), -1);
    }
  }

  private void unbindParameters(Variables boundParams)
  {
    for (Map.Entry<String, String> boundParam : boundParams.entrySet())
    {
      gameState.getVariables().unsetValue(boundParam.getKey());
    }
  }

  public String toString()
  {
    return "StepInvoker@" + hashCode() + "[" + step.procName + "]";
  }
}
