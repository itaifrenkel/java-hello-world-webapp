package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.AbstractCmd;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import java.io.IOException;

public class SendMessageCmd extends AbstractCmd
{
  private final int chatId;
  private final String message;
  private final String replyMarkup;

  public SendMessageCmd(int chatId, String message)
  {
    this(chatId, message, null);
  }

  SendMessageCmd(int chatId, String message, String replyMarkup)
  {
    this.chatId = chatId;
    this.message = message;
    this.replyMarkup = replyMarkup;
  }

  @Override
  public void execute() throws IOException
  {
    if (replyMarkup == null)
    {
      TelegramMessageSender.sendMessage(chatId, message);
    }
    else
    {
      TelegramMessageSender.sendMessage(chatId, message, replyMarkup);
    }
  }
}
