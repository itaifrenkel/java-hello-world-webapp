package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.gson.game.Step;

import java.util.Map;

public class StepInvoker2
{
  private final Step step;
  private final GameState gameState;
  private ActionInvoker2 invoker = null;

  StepInvoker2(Step step, GameState gameState)
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
    if (isComplete())
    {
      unbindParameters();
    }
    mapResults(results);
    return results;
  }

  private void bindParameters()
  {
    Variables parameters = new Variables();
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
    gameState.getVariables().pushNewVariablesStackFrame("call " + step.procName , parameters);
  }

  private InvocationResults invokeStep() throws ActionInvocationException
  {
    if (invoker == null)
    {
      invoker = new ActionInvoker2(step.procName, gameState);

    }
    InvocationResults results = invoker.invokeNext();
    return results;
  }

  boolean isComplete()
  {
    return !invoker.hasNext();
  }

  private void mapResults(InvocationResults results)
  {
    if (step.outputMappings != null)
    {
      for (Map.Entry<String, String> outputMapping : step.outputMappings.entrySet())
      {
        String returnedVarName = outputMapping.getKey();
        String resultValue = results.getVariables().get(returnedVarName);
        String mapTo = outputMapping.getValue();
        gameState.getVariables().setValue(mapTo, resultValue);
      }
    }
  }

  private void unbindParameters()
  {
    gameState.getVariables().dropStackFrame();
  }
}
