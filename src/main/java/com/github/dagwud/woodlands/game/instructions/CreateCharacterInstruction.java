package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvokerDelegate;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.gson.game.ParamMappings;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;
import java.util.HashMap;

public class CreateCharacterInstruction extends GameInstruction
{
  private final int chatId;

  CreateCharacterInstruction(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute(GameState gameState) throws IOException, ActionInvocationException
  {
    TelegramMessageSender.sendMessage(chatId, "Here we go!");

    gameState.getVariables().setValue("chatId", String.valueOf(chatId));

    CallDetails callDetails = new CallDetails(new HashMap<>(), new ParamMappings());
    ActionInvokerDelegate.invoke(gameState, "PlayerSetup");
  }
}
