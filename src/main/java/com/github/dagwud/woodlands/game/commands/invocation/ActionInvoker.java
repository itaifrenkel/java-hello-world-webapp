package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.gson.game.ParamMappings;

import java.util.Map;

public abstract class ActionInvoker
{
  final CallDetails callDetails;

  ActionInvoker(CallDetails callDetails)
  {
    this.callDetails = callDetails;
  }

  final InvocationResults invoke(GameState gameState) throws ActionInvocationException
  {
    if (!this.getActionName().equals("PushVariablesAction"))
    {
//      System.out.println(getActionName() + " before call: \n" + gameState.getVariables());
    }

    InvocationResults results = doInvoke(gameState, callDetails.getOutputMappings());
    if (results.getReturnMode() != ReturnMode.SUSPEND)
    {
      complete(gameState, results, callDetails.getOutputMappings());
    }

    return results;
  }

  void complete(GameState gameState, InvocationResults results, ParamMappings outputMappings)
  {
    Variables resultVariables = results.getVariables();
    mapResults(resultVariables, gameState.getVariables(), outputMappings);
//    gameState.getVariables().dropStackFrame();
    if (resultVariables != null && !resultVariables.isEmpty())
    {
//      System.out.println(getActionName() + " after call: \n" + gameState.getVariables());
    }
  }

  abstract String getActionName();

  abstract InvocationResults doInvoke(GameState gameState, ParamMappings outputMappings) throws ActionInvocationException;

  private static void mapResults(Variables results, VariableStack callContext, ParamMappings outputMappings)
  {
    for (Map.Entry<String, String> outputMapping : outputMappings.entrySet()) // todo is it necessary to have OM on context now?
    {
      String outputName = outputMapping.getKey();
      String mapToVariableName = outputMapping.getValue();
      String outputValue = results.get(outputName);
      //todo null handling of outputName==null - mapping refers to an invalid output
      if (outputValue != null && outputValue.startsWith("$"))
      {
        outputValue = ValueResolver.resolve(outputValue, callContext);
      }
      callContext.setValue(mapToVariableName, outputValue, -1);
    }

    if (null != results)
    {
      for (Map.Entry<String, String> result : ((Map<String, String>) results).entrySet())
      {
        if (VariableStack.isGlobalVariable(result.getKey()))
        {
          callContext.setValue(result.getKey(), result.getValue());
        }
      }
    }
  }
}
