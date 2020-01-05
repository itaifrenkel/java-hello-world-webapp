package com.github.dagwud.woodlands.web;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.instructions.GameInstruction;
import com.github.dagwud.woodlands.game.instructions.GameInstructionFactory;
import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.telegram.Update;

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
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    // todo verify request came from telegram - token in request
    Update update = GsonHelper.readJSON(req.getReader(), Update.class);
    try
    {
      GameState gameState = GameStatesRegistry.lookup(update.message.chat.id);
      GameInstruction instruction = GameInstructionFactory.instance().create(update, gameState);
      instruction.execute(gameState);
    }
    catch (Exception e)
    {
      throw new ServletException("Internal Error", e); //todo nuke e! Don't expose!
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException
  {
    throw new UnavailableException("Not available");
  }

}
