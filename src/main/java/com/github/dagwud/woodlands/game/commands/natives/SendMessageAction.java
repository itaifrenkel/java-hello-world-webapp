package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.*;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class SendMessageAction extends NativeAction
{
  private static final String PARAMETER_NAME_MESSAGE = "Message";

  @Override
  public void verifyParameters(Variables parameters) throws ActionParameterException
  {
    parameters.verifyRequiredParameter("SendMessage", PARAMETER_NAME_MESSAGE);
  }

  @Override
  public ActionParameters invoke(ActionCallContext context)
  {
    String message = context.getCallParameters().lookupVariableValue(PARAMETER_NAME_MESSAGE);
    System.out.println(">>> " + message);
    return new ActionParameters("test", new HashMap<String, String>());
  }
}
