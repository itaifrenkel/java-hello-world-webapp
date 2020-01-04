package com.github.dagwud.woodlands.telegram;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public abstract class TelegramHelper
{
  private static final String BOT_TOK = "802063349:AAEpMcSlEzIbk5Ue3B0lSaLuO24fm-JI9hc"; // TODO REMOVE AND REGENERATE!!!

  public static void sendMessage(int chatId, String message) throws IOException
  {
    Map<String, String> params = buildParams(chatId, message);
    callTelegram("sendMessage", params);
  }

  private static String callURL(String url) throws IOException
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

  private static Map<String, String> buildParams(int chatId, String message)
  {
    Map<String, String> params = new HashMap<>();
    params.put("chat_id", String.valueOf(chatId));
    params.put("text", message);
    return params;
  }

  private static String encode(String baseUrl, Map<String, String> requestParams)
  {
    return requestParams.keySet().stream()
            .map(key -> key + "=" + encodeParameter(requestParams.get(key)))
            .collect(joining("&", baseUrl + "?", ""));
  }

  private static String encodeParameter(String parameter)
  {
    try
    {
      return URLEncoder.encode(parameter, StandardCharsets.UTF_8.toString());
    }
    catch (UnsupportedEncodingException e)
    {
      throw new RuntimeException(e);
    }
  }

  private static String callTelegram(String method, Map<String, String> params) throws IOException
  {
    String endpoint = buildTelegramURL(method);
    String url = encode(endpoint, params);
    System.out.println("CALLING: " + url);
    return callURL(url);
  }

  private static String buildTelegramURL(String method)
  {
    return "https://api.telegram.org/bot" + BOT_TOK + "/" + method;
  }

}
