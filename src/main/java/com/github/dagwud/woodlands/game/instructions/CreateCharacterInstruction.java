package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.telegram.TelegramHelper;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;

public class CreateCharacterInstruction extends GameInstruction
{
  private final int chatId;

  CreateCharacterInstruction(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute(GameState gameState) throws IOException
  {
    TelegramMessageSender.sendMessage(chatId, "Here we go!");
  }
}
