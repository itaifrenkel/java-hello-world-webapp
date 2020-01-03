package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionCallContext;
import com.github.dagwud.woodlands.game.commands.invocation.ActionParameters;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class SetVarAction extends NativeAction
{
  @Override
  public ActionParameters invoke(ActionCallContext parameters)
  {
    String varSet = parameters.getCallParameters().lookupVariableValue("VarSet");
    String varName = "__" + varSet + "." + parameters.getCallParameters().lookupVariableValue("VarName");
    String varValue = parameters.getCallParameters().lookupVariableValue("VarValue");
    System.out.println("SET VAR: " + varName + " = " + varValue);
    ActionParameters result = new ActionParameters("setvarparams", new HashMap<String, String>());
    result.putParameterValue(varName, varValue);
    return result;
  }
}
