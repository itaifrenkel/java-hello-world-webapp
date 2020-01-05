package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.*;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class SendMessageAction extends NativeAction
{

  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails) throws IOException
  {
    String message = gameState.getVariables().lookupVariableValue("MessageToPrint");
    String chatId = gameState.getVariables().lookupVariableValue("chatId");
    if (!chatId.equals("-1"))
    {
      TelegramMessageSender.sendMessage(Integer.parseInt(chatId), message);
    }
    else
    {
      System.out.println(">>> " + message);
    }
    return new InvocationResults(new Variables("test", new HashMap<>()));
  }
}
