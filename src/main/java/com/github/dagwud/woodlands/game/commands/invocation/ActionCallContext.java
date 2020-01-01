package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.HashMap;
import java.util.Map;

public class ActionCallContext
{
  private final ActionParameters callParameters;

  ActionCallContext()
  {
    HashMap<String, String> emptyParameters = new HashMap<>(0);
    this.callParameters = new ActionParameters(emptyParameters);
  }

  ActionCallContext(ActionCallContext baseContext, Map<String, String> callParameters)
  {
    this.callParameters = new ActionParameters(baseContext.callParameters);
    this.callParameters.putAll(callParameters);
  }

  public ActionParameters getCallParameters()
  {
    return callParameters;
  }
}
