package com.github.dagwud.woodlands.web;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.instructions.GameInstruction;
import com.github.dagwud.woodlands.game.instructions.GameInstructionFactory;
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
import java.io.IOException;

@WebServlet(name = "TelegramServlet", urlPatterns = "/telegram")
public class TelegramServlet extends HttpServlet
{
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
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
      try
      {
        while (t != null)
        {
          TelegramMessageSender.sendMessage(determineChatId(update), t.toString());
          t = t.getCause();
        }
      }
      catch (Exception f)
      {
        f.printStackTrace();
      }
    }
  }

  public void processTelegramUpdate(Update update) throws ActionInvocationException, IOException
  {
    int chatId = determineChatId(update);

    // todo verify request came from telegram - token in request
    String text = determineText(update);

    GameState gameState = GameStatesRegistry.lookup(chatId);
    synchronized (GameStatesRegistry.lookup(chatId))
    {
      if (gameState.suspended != null)
      {
        gameState.getVariables().setValue("__buffer", text);
        gameState.suspended.invokeAction();
      }
      else
      {
        GameInstruction instruction = GameInstructionFactory.instance().create(update);
        instruction.execute(gameState);
      }
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
