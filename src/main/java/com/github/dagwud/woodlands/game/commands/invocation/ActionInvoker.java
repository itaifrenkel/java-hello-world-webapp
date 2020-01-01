package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;

abstract class ActionInvoker
{
  abstract void verifyParameters(ActionParameters parameters) throws ActionParameterException;

  abstract ActionResults invoke(ActionCallContext context) throws ActionInvocationException;

}
