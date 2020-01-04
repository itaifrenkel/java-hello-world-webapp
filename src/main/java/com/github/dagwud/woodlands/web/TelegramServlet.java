package com.github.dagwud.woodlands.web;

import com.github.dagwud.woodlands.gson.game.Root;
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
    Update update = GsonHelper.readJSON(req.getReader(), Update.class);
    try
    {
      String info = parse(update);
    }
    catch (Exception e)
    {
      throw new ServletException("Internal Error", e); //todo nuke e! Don't expose!
    }
  }

  private String parse(Update update)
  {
    String info = "";
    if (update == null)
    {
      throw new IllegalArgumentException("Update must be provided");
    }

    if (update.message == null)
    {
      throw new IllegalArgumentException("Update must contain a message");
    }

    if (update.message.from == null)
    {
      throw new IllegalArgumentException("Message has no sender");
    }

    info += update.message.from.firstName;
    info += " says: ";
    if (update.message.text == null)
    {
      throw new IllegalArgumentException("No message text provided");
    }
    info += update.message.text;
    return info;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException
  {
    throw new UnavailableException("Not available");
  }

}
