package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionCallContext;
import com.github.dagwud.woodlands.game.commands.invocation.ActionResults;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

public abstract class NativeAction
{
  public void verifyParameters(Variables parameters) throws ActionParameterException
  {
  }

  public abstract ActionResults invoke(ActionCallContext context);
}
