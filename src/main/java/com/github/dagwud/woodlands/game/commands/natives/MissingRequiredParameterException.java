package com.github.dagwud.woodlands.game.commands.natives;

public class MissingRequiredParameterException extends ActionParameterException
{
  public MissingRequiredParameterException(String actionName, String missingParameter)
  {
    super("Call to \"" + actionName + "\" requires parameter " + missingParameter + " to be provided");
  }

}
