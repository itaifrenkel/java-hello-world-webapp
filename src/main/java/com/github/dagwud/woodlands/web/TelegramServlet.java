package com.github.dagwud.woodlands.web;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationPlanExecutor;
import com.github.dagwud.woodlands.game.instructions.GameInstruction;
import com.github.dagwud.woodlands.game.instructions.GameInstructionFactory;
import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.telegram.CallbackQuery;
import com.github.dagwud.woodlands.gson.telegram.Chat;
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
    Update update = GsonHelper.readJSON(req.getReader(), Update.class);
    processTelegramUpdate(update);
  }

  public void processTelegramUpdate(Update update)
  {
    int chatId = determineChatId(update);

    try
    {
      // todo verify request came from telegram - token in request
      String text = determineText(update);

      GameState gameState = GameStatesRegistry.lookup(chatId);
      if (gameState.suspended != null)
      {
        gameState.suspended.getGameState().getVariables().setValue("__buffer", text);
        ActionInvocationPlanExecutor.resume(gameState.suspended);
      }
      else
      {
        GameInstruction instruction = GameInstructionFactory.instance().create(update, gameState);
        instruction.execute(gameState);
      }
    }
    catch (Exception e2)
    {
      Throwable e = e2;
      e.printStackTrace();
      try
      {
        while (e != null)
        {
          TelegramMessageSender.sendMessage(chatId, e.toString());
          e = e.getCause();
        }
      }
      catch (Exception f)
      {
        f.printStackTrace();
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
