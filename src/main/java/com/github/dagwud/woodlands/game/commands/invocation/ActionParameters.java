package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.ActionParameterException;
import com.github.dagwud.woodlands.game.commands.natives.MissingRequiredParameterException;

import java.util.HashMap;
import java.util.Map;

public class ActionParameters extends HashMap<String, String>
{
  ActionParameters(Map<String, String> callParameters)
  {
    for (Entry<String, String> callParameter : callParameters.entrySet())
    {
      put(callParameter.getKey(), callParameter.getValue());
    }
  }

  @Override
  public String put(String key, String value)
  {
    String paramValue = ValueResolver.resolve(value, this);
    return super.put(key, paramValue);
  }

  @Override
  public void putAll(Map<? extends String, ? extends String> m)
  {
    Map<String, String> mResolved = new HashMap<>(m);
    for (String k : m.keySet())
    {
      String valueExpr = m.get(k);
      valueExpr = ValueResolver.resolve(valueExpr, this);
      mResolved.put(k, valueExpr);
    }
    super.putAll(mResolved);
  }

  public void verifyRequiredParameter(String actionName, String requiredParameterName) throws ActionParameterException
  {
    if (!containsKey(requiredParameterName))
    {
      throw new MissingRequiredParameterException(actionName, requiredParameterName);
    }
  }

}
