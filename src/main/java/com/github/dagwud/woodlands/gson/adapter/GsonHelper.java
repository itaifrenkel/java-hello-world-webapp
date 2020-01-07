package com.github.dagwud.woodlands.gson.adapter;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;

public abstract class GsonHelper
{
  private GsonHelper()
  {
  }

  private static Gson createGson()
  {
    return createBuilder().create();
  }

  public static <T> T readJSON(File file, Class<T> classOfT)
  {
    String json = readFile(file);
    return readJSON(json, classOfT);
  }

  public static <T> T readJSON(Reader reader, Class<T> classOfT)
  {
    Gson gson = createGson();
    return gson.fromJson(reader, classOfT);
  }

  static <T> T readJSON(String json, Class<T> classOfT)
  {
    byte[] chars = json.getBytes();
    InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(chars));
    return readJSON(reader, classOfT);
  }

  private static GsonBuilder createBuilder()
  {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Variables.class, new KVPairAdapter());
    return builder;
  }

  private static String readFile(File file)
  {
    byte[] bytes;
    try
    {
      bytes = Files.readAllBytes(file.toPath());
    }
    catch (IOException e)
    {
      throw new RuntimeException("Unable to initialize", e);
    }
    return new String(bytes);
  }

}
