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
  }

  @Override
  public Variables invoke(GameState gameState) throws IOException
  {
    String message = gameState.getVariables().lookupVariableValue(PARAMETER_NAME_MESSAGE);
    String chatId = gameState.getVariables().lookupVariableValue("chatId");
    if (!chatId.equals("-1"))
    {
      TelegramMessageSender.sendMessage(Integer.parseInt(chatId), message);
    }
    else
    {
      System.out.println(">>> " + chatId);
    }
    return new Variables("test", new HashMap<>());
  }
}
