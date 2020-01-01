package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionParameters;
import com.github.dagwud.woodlands.game.commands.invocation.ActionResults;

import java.util.Map;

@SuppressWarnings("unused") // called at runtime via reflection
public class SendMessageAction extends NativeAction
{
  private static final String PARAMETER_NAME_MESSAGE = "Message";

  @Override
  public ActionResults invoke(ActionParameters callParameters)
  {
    String message = callParameters.get(PARAMETER_NAME_MESSAGE);
    System.out.println(">>> " + message);
    return new ActionResults();
  }
}
