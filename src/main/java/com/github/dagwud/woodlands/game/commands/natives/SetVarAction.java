package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionParameters;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class SetVarAction extends NativeAction
{
  @Override
  public ActionParameters invoke(Variables parameters)
  {
    String varSet = parameters.lookupVariableValue("VarSet");
    String varName = "__" + varSet + "." + parameters.lookupVariableValue("VarName");
    String varValue = parameters.lookupVariableValue("VarValue");
    System.out.println("SET VAR: " + varName + " = " + varValue);
    ActionParameters result = new ActionParameters("setvarparams", new HashMap<String, String>());
    result.putParameterValue(varName, varValue);
    return result;
  }
}
