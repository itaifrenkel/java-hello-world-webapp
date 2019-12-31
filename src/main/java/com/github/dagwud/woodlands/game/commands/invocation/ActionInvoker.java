package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.Map;

abstract class ActionInvoker
{
  private final Map<String, String> callParameters;

  ActionInvoker(Map<String, String> callParameters)
  {
    this.callParameters = callParameters;
  }

  abstract ActionResults invoke() throws ActionInvocationException;

  String resolveValue(String valueExpression)
  {
    return valueExpression;
  }
}
