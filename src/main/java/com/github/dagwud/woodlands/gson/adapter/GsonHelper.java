package com.github.dagwud.woodlands.gson.adapter;

import com.github.dagwud.woodlands.gson.ParamMappings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class GsonHelper
{
  private GsonHelper()
  {
  }

  public static Gson createGson()
  {
    return createBuilder().create();
  }

  private static GsonBuilder createBuilder()
  {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ParamMappings.class, new KVPairAdapter());
    return builder;
  }

}
