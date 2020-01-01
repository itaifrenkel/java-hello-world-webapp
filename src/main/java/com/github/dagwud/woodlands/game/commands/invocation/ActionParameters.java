package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.game.commands.natives.MissingRequiredParameterException;

import java.util.HashMap;
import java.util.Map;

public class ActionParameters extends HashMap<String, String>
{
  ActionParameters(Map<String, String> callParameters)
  {
    this.putAll(callParameters);
  }

  public void verifyRequiredParameter(String actionName, String requiredParameterName) throws ActionParameterException
  {
    if (!containsKey(requiredParameterName))
    {
      throw new MissingRequiredParameterException(actionName, requiredParameterName);
    }
  }

}
