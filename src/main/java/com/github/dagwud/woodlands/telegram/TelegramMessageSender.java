package com.github.dagwud.woodlands.telegram;

import com.github.dagwud.woodlands.game.messaging.IMessageSender;

import java.io.IOException;
import java.util.Map;

public class TelegramMessageSender extends TelegramHelper implements IMessageSender
{
  public void sendMessage(int chatId, String message) throws IOException
  {
    sendMessage(chatId, message, null);
  }

  public void sendMessage(int chatId, String message, String replyMarkup) throws IOException
  {
    Map<String, String> params = buildUrlParams(chatId, message, replyMarkup);
    if (chatId > 0)
    {
      callTelegram("sendMessage", params);
    }
    else
    {
      System.out.println("SEND MESSAGE: " + params);
    }
  }

}
