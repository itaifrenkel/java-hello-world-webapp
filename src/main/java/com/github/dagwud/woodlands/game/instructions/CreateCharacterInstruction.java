package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.telegram.TelegramHelper;

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
    TelegramHelper.sendMessage(chatId, "Here we go!");
  }
}
