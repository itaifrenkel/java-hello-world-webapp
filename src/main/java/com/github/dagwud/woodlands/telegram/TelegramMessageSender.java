package com.github.dagwud.woodlands.telegram;

import com.github.dagwud.woodlands.game.log.Logger;
import com.github.dagwud.woodlands.game.messaging.IMessageSender;

import java.io.IOException;
import java.util.Map;

public class TelegramMessageSender extends TelegramHelper implements IMessageSender
{
  public void sendMessage(long chatId, String message) throws IOException
  {
    sendMessage(chatId, message, null);
  }

  public void sendMessage(long chatId, String message, String replyMarkup) throws IOException
  {
    Map<String, String> params = buildUrlParams(chatId, message, replyMarkup);
    if (chatId > 0L)
    {
      callTelegram("sendMessage", params);
    }
  }

}
