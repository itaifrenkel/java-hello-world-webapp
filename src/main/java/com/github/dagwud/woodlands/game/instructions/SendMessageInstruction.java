package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.telegram.TelegramHelper;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;

public class SendMessageInstruction extends GameInstruction
{
  private final int chatId;
  private final String message;

  SendMessageInstruction(int chatId, String message)
  {
    this.chatId = chatId;
    this.message = message;
  }

  @Override
  public void execute(GameState gameState) throws IOException
  {
    TelegramMessageSender.sendMessage(chatId, message);
  }
}
