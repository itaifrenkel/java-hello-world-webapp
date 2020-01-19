package com.github.dagwud.woodlands.game.commands.core;

import com.github.dagwud.woodlands.game.messaging.MessagingFactory;

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
    MessagingFactory.create().sender().sendMessage(chatId, message, replyMarkup);
  }
}
