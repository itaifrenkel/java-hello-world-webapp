package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;

abstract class ActionInvoker
{
  abstract void verifyParameters(Variables parameters) throws ActionParameterException;

  abstract ActionResults invoke(ActionCallContext context) throws ActionInvocationException;

}
