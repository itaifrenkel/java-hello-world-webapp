package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.gson.game.ParamMappings;

import java.util.Map;

abstract class ActionInvoker
{
  final InvocationResults invoke(GameState gameState, CallDetails callDetails) throws ActionInvocationException
  {
    gameState.getVariables().pushNewVariablesStackFrame(getActionName(), callDetails.getCallParameters());
//    System.out.println(getActionName() + " before call: \n" + variables.getCallParameters());

    InvocationResults results = doInvoke(gameState, callDetails.getOutputMappings());

    gameState.getVariables().dropStackFrame();
    Variables resultVariables = results.getVariables();
    mapResults(resultVariables, gameState.getVariables(), callDetails.getOutputMappings());
    if (resultVariables != null && !resultVariables.isEmpty())
    {
      System.out.println(getActionName() + " after call: \n" + gameState.getVariables());
    }
    return results;
  }

  abstract String getActionName();

  abstract InvocationResults doInvoke(GameState gameState, ParamMappings outputMappings) throws ActionInvocationException;

  private static void mapResults(Variables results, VariableStack callContext, ParamMappings outputMappings)
  {
    for (Map.Entry<String, String> outputMapping : outputMappings.mappings.entrySet()) // todo is it necessary to have OM on context now?
    {
      String outputName = outputMapping.getKey();
      String mapToVariableName = outputMapping.getValue();
      String outputValue = results.get(outputName);
      //todo null handling - mapping refers to an invalid output
      callContext.setValue(mapToVariableName, outputValue);
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
