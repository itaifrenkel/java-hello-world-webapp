package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

import java.text.NumberFormat;

@SuppressWarnings("unused") // called at runtime via reflection
public class SetVarAction extends NativeAction
{
  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails)
  {
    String varSet = gameState.getVariables().lookupVariableValue("VarSet");
    String varName = varSet + "." + gameState.getVariables().lookupVariableValue("VarName");
    String varValue = gameState.getVariables().lookupVariableValue("VarValue");
    if (varValue.startsWith("eval(") && varValue.endsWith(")"))
    {
      varValue = evaluate(varValue);
    }
    gameState.getVariables().setValue(varName, varValue);
    return new InvocationResults(new Variables());
  }

  private String evaluate(String expr)
  {
    expr = expr.substring("eval(".length());
    expr = expr.substring(0, expr.length() - ")".length());
    double result = MathEvaluator.eval(expr);
    if (result == (long)result)
    {
      return String.format("%d", (long)result);
    }
    return String.format("%s", result);
  }
}
