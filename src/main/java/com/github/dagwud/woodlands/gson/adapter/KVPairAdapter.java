package com.github.dagwud.woodlands.gson.adapter;

import com.github.dagwud.woodlands.gson.game.ParamMappings;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

class KVPairAdapter implements JsonDeserializer
{
  public ParamMappings deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
  {
    JsonObject jsonParamMappings = jsonElement.getAsJsonObject();
    HashMap<String, String> mappings = new HashMap<>();
    for (String key : jsonParamMappings.keySet())
    {
      String value = jsonParamMappings.get(key).getAsString();
      mappings.put(key, value);
    }

    return new ParamMappings(mappings);
  }
}
