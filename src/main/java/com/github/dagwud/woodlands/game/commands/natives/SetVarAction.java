package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class SetVarAction extends NativeAction
{
  @Override
  public Variables invoke(GameState gameState)
  {
    String varSet = gameState.getVariables().lookupVariableValue("VarSet");
    String varName = "__" + varSet + "." + gameState.getVariables().lookupVariableValue("VarName");
    String varValue = gameState.getVariables().lookupVariableValue("VarValue");
    System.out.println("SET VAR: " + varName + " = " + varValue);
    Variables result = new Variables("setvarparams", new HashMap<>());
    result.put(varName, varValue);
    return result;
  }
}
