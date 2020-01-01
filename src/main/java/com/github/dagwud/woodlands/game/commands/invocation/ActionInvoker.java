package com.github.dagwud.woodlands.game.commands.invocation;

abstract class ActionInvoker
{
  abstract ActionResults invoke(ActionParameters parameters) throws ActionInvocationException;

  String resolveValue(String valueExpression)
  {
    return valueExpression;
  }
}
