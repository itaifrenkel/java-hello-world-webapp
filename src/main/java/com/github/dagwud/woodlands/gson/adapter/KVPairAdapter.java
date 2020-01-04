package com.github.dagwud.woodlands.gson.adapter;

import com.github.dagwud.woodlands.gson.game.ParamMappings;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class KVPairAdapter implements JsonDeserializer
{
  public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
  {
    JsonObject jsonParamMappings = jsonElement.getAsJsonObject();
    HashMap<String, String> mappings = new HashMap<String, String>();
    for (String key : jsonParamMappings.keySet())
    {
      String value = jsonParamMappings.get(key).getAsString();
      mappings.put(key, value);
    }

    ParamMappings paramMappings = new ParamMappings();
    paramMappings.mappings = mappings;
    return paramMappings;
  }
}
