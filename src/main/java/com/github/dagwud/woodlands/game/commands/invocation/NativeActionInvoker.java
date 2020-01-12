package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.natives.NativeAction;

import java.util.Map;

class NativeActionInvoker
{
  final CallDetails callDetails;
  private final NativeAction nativeAction;

  NativeActionInvoker(NativeAction action, CallDetails callDetails)
  {
    this.callDetails = callDetails;
    this.nativeAction = action;
  }

  private static void mapResults(Variables results, VariableStack callContext, Variables outputMappings)
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

  String getActionName()
  {
    return nativeAction.getClass().getSimpleName() + " (native)";
  }

  InvocationResults doInvoke(GameState gameState, Variables outputMappings) throws ActionInvocationException
  {
    try
    {
      return nativeAction.invoke(gameState, callDetails);
    }
    catch (Exception e)
    {
      throw new ActionInvocationException(e);
    }
  }

  @Override
  public String toString()
  {
    return "NativeActionInvoker{" +
            "nativeAction=" + nativeAction.getClass().getSimpleName() +
            '}';
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

  void complete(GameState gameState, InvocationResults results, Variables outputMappings)
  {
    Variables resultVariables = results.getVariables();
    mapResults(resultVariables, gameState.getVariables(), outputMappings);
    if (resultVariables != null && !resultVariables.isEmpty())
    {
//      System.out.println(getActionName() + " after call: \n" + gameState.getVariables());
    }
  }
}
