package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;

public abstract class NativeAction
{
  public void verifyParameters(VariableStack parameters) throws ActionParameterException
  {
  }

  public abstract Variables invoke(VariableStack context);
}
