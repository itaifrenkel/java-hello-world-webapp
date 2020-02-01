package com.github.dagwud.woodlands.telegram;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

abstract class TelegramHelper
{
  private static final String BOT_TOK = System.getenv().get("TELEGRAM_TOKEN");

  static void callTelegram(String method, Map<String, String> params) throws IOException
  {
    String endpoint = buildTelegramURL(method);
    String url = encode(endpoint, params);
    //Logger.log("CALL: " + url);
    callURL(url);
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

  static Map<String, String> buildUrlParams(int chatId, String message)
  {
    return buildUrlParams(chatId, message, null);
  }

  static Map<String, String> buildUrlParams(int chatId, String message, String replyMarkup)
  {
    Map<String, String> params = new HashMap<>();
    params.put("chat_id", String.valueOf(chatId));
    params.put("text", message);
    if (null != replyMarkup)
    {
      params.put("reply_markup", replyMarkup);
    }
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

  private static String buildTelegramURL(String method)
  {
    return "https://api.telegram.org/bot" + BOT_TOK + "/" + method;
  }

}
