package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;

public class ShowHelpInstruction extends GameInstruction
{
  private static final String HELP_MESSAGE = "The following commands are available:\n" +
          "* /new - create a new character\n" +
          "* /help - show this help info";

  private final int chatId;

  ShowHelpInstruction(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute(GameState gameState) throws IOException
  {
    TelegramMessageSender.sendMessage(chatId, HELP_MESSAGE);
  }
}
