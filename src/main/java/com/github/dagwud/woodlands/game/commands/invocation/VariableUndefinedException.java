package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;

public class VariableUndefinedException extends WoodlandsRuntimeException
{
  VariableUndefinedException(String varName, VariableStack definedVariables)
  {
    super("Variable named '" + varName + "' is not defined: \n" + definedVariables.pretty());
  }
}
