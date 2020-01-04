package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.*;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class SendMessageAction extends NativeAction
{
  private static final String PARAMETER_NAME_MESSAGE = "Message";

  @Override
  public void verifyParameters(VariableStack parameters) throws ActionParameterException
  {
    parameters.verifyRequiredParameter("SendMessage", PARAMETER_NAME_MESSAGE);
  }

  @Override
  public Variables invoke(GameState gameState, VariableStack context) throws IOException
  {
    String message = context.lookupVariableValue(PARAMETER_NAME_MESSAGE);
    String chatId = context.lookupVariableValue("chatId");
    TelegramMessageSender.sendMessage(Integer.parseInt(chatId), message);
    return new Variables("test", new HashMap<String, String>());
  }
}
