package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvokerDelegate;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;
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

    VariableStack variables = new VariableStack();
    variables.setValue("chatId", String.valueOf(chatId));

    ActionInvokerDelegate.invoke(gameState, "PlayerSetup", new HashMap<>(), new ParamMappings());
  }
}
