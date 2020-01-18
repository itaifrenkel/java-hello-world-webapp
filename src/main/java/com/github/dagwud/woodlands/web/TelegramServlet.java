package com.github.dagwud.woodlands.web;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.instructions.CommandFactory;
import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.telegram.CallbackQuery;
import com.github.dagwud.woodlands.gson.telegram.Update;
import com.github.dagwud.woodlands.telegram.TelegramMessageSender;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TelegramServlet", urlPatterns = "/telegram")
public class TelegramServlet extends HttpServlet
{
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
  {
    Update update = null;
    try
    {
      update = GsonHelper.readJSON(req.getReader(), Update.class);
      processTelegramUpdate(update);
    }
    catch (Exception e)
    {
      Throwable t = e;
      t.printStackTrace();
      if (update != null)
      {
        sendStackTraceIfPossible(update, t);
      }
    }
  }

  private void sendStackTraceIfPossible(Update sendTo, Throwable stackToSend)
  {
    try
    {
      while (stackToSend != null)
      {
        TelegramMessageSender.sendMessage(determineChatId(sendTo), stackToSend.toString());
        stackToSend = stackToSend.getCause();
      }
    }
    catch (Exception f)
    {
      f.printStackTrace();
    }
  }

  public void processTelegramUpdate(Update update) throws Exception
  {
    int chatId = determineChatId(update);

    // todo verify request came from telegram - token in request
    String text = determineText(update);

    GameState gameState = GameStatesRegistry.lookup(chatId);
    synchronized (GameStatesRegistry.lookup(chatId))
    {
      AbstractCmd instruction = CommandFactory.instance().create(update, gameState);
      instruction.execute();
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException
  {
    throw new UnavailableException("Not available");
  }

  private String determineText(Update update)
  {
    if (update.message != null)
    {
      return update.message.text;
    }
    CallbackQuery callback = update.callbackQuery;
    return callback.data;
  }

  private int determineChatId(Update update)
  {
    if (update.message != null)
    {
      return update.message.chat.id;
    }
    CallbackQuery callback = update.callbackQuery;
    return callback.message.chat.id;
  }

}
