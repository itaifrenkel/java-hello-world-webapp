package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;

class VariableUndefinedException extends WoodlandsRuntimeException
{
  VariableUndefinedException(String varName)
  {
    super("Variable named '" + varName + "' is not defined");
  }
}
