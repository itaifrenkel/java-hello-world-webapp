package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.gson.ParamMappings;

import java.util.Map;

abstract class ActionInvoker
{
  abstract void verifyParameters(Variables parameters) throws ActionParameterException;

  final void invoke(Variables context, Map<String, String> callParameters, ParamMappings outputMappings) throws ActionInvocationException
  {
    context.pushNewVariablesStackFrame(getActionName(), callParameters);
//    System.out.println(getActionName() + " before call: \n" + context.getCallParameters());

    ActionParameters results = doInvoke(context, outputMappings);

    context.dropStackFrame();
    mapResults(results, context, outputMappings);
    if (results != null && !results.getValues().isEmpty())
    {
      System.out.println(getActionName() + " after call: \n" + context);
    }
  }

  abstract String getActionName();

  abstract ActionParameters doInvoke(Variables context, ParamMappings outputMappings) throws ActionInvocationException;

  private static void mapResults(ActionParameters results, Variables callContext, ParamMappings outputMappings)
  {
    for (Map.Entry<String, String> outputMapping : outputMappings.mappings.entrySet()) // todo is it necessary to have OM on context now?
    {
      String outputName = outputMapping.getKey();
      String mapToVariableName = outputMapping.getValue();
      String outputValue = results.getParameterValue(outputName);
      callContext.setValue(mapToVariableName, outputValue);
    }

    if (null != results)
    {
      for (Map.Entry<String, String> result : results.getValues().entrySet())
      {
        if (Variables.isGlobalVariable(result.getKey()))
        {
          callContext.setValue(result.getKey(), result.getValue());
        }
      }
    }
  }
}
