package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionParameters;
import com.github.dagwud.woodlands.game.commands.invocation.ActionResults;

public abstract class NativeAction
{
  public void verifyParameters(ActionParameters parameters) throws ActionParameterException
  {
  }

  public abstract ActionResults invoke(ActionParameters parameters);
}
