package com.github.dagwud.woodlands.gson.adapter;

import com.github.dagwud.woodlands.gson.ParamMappings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public abstract class GsonHelper
{
  private GsonHelper()
  {
  }

  public static Gson createGson()
  {
    return createBuilder().create();
  }

  public static <T> T readJSON(String json, Class<T> classOfT)
  {
    Gson gson = createGson();
    byte[] chars = json.getBytes();
    InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(chars));
    return gson.fromJson(reader, classOfT);
  }

  private static GsonBuilder createBuilder()
  {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ParamMappings.class, new KVPairAdapter());
    return builder;
  }

}
