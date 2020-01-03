package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class SetVarAction extends NativeAction
{
  @Override
  public Variables invoke(VariableStack parameters)
  {
    String varSet = parameters.lookupVariableValue("VarSet");
    String varName = "__" + varSet + "." + parameters.lookupVariableValue("VarName");
    String varValue = parameters.lookupVariableValue("VarValue");
    System.out.println("SET VAR: " + varName + " = " + varValue);
    Variables result = new Variables("setvarparams", new HashMap<String, String>());
    result.putParameterValue(varName, varValue);
    return result;
  }
}
