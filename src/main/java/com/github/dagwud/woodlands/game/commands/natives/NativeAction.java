package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionParameters;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

public abstract class NativeAction
{
  public void verifyParameters(Variables parameters) throws ActionParameterException
  {
  }

  public abstract ActionParameters invoke(Variables context);
}
