package com.github.dagwud.woodlands.web;

import com.github.dagwud.woodlands.gson.adapter.GsonHelper;
import com.github.dagwud.woodlands.gson.telegram.Update;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@WebServlet(name = "TelegramServlet", urlPatterns = "/telegram")
public class TelegramServlet extends HttpServlet
{
  private static final String BOT_TOK = "802063349:AAEpMcSlEzIbk5Ue3B0lSaLu024fm-JI9hc"; // TODO REMOVE AND REGENERATE!!!

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    // todo verify request came from telegarm - token in request
    Update update = GsonHelper.readJSON(req.getReader(), Update.class);
    try
    {
      String info = parse(update);

      Map<String, String> params = buildParams(update.message.chat.id, info);
      callTelegram("sendMessage", params);
      throw new RuntimeException(info);
    }
    catch (Exception e)
    {
      throw new ServletException("Internal Error", e); //todo nuke e! Don't expose!
    }
  }

  private Map<String, String> buildParams(int chatId, String message)
  {
    Map<String, String> params = new HashMap<>();
    params.put("chat_id", String.valueOf(chatId));
    params.put("text", message);
    return params;
  }

  private String encode(String baseUrl, Map<String, String> requestParams)
  {
    return requestParams.keySet().stream()
            .map(key -> key + "=" + encode(requestParams.get(key)))
            .collect(joining("&", baseUrl + "?", ""));
  }

  private void callTelegram(String method, Map<String, String> params) throws IOException
  {
    String endpoint = buildTelegramURL(method);
    String url = encode(endpoint, params);
    String result = callURL(url);
  }

  private String encode(String params)
  {
    try
    {
      return URLEncoder.encode(params, StandardCharsets.UTF_8.toString());
    }
    catch (UnsupportedEncodingException e)
    {
      throw new RuntimeException(e);
    }
  }

  private String callURL(String url) throws IOException
  {
    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

    InputStream inputStream;
    if (conn.getResponseCode() != 200)
    {
      inputStream = conn.getErrorStream();
    }
    else
    {
      inputStream = conn.getInputStream();
    }

    StringBuilder resp = new StringBuilder();
    try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream)))
    {
      String currentLine;
      while ((currentLine = in.readLine()) != null)
      {
        resp.append(currentLine).append("\n");
      }
    }

    if (conn.getResponseCode() != 200)
    {
      throw new IOException("Error " + conn.getResponseCode() + ": " + resp.toString());
    }
    return resp.toString();
  }

  private String buildTelegramURL(String method)
  {
    return "https://api.telegram.org/bot" + BOT_TOK + "/" + method;
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
