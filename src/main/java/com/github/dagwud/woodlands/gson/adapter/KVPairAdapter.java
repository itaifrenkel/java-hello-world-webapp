package com.github.dagwud.woodlands.gson.adapter;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.google.gson.*;

import java.lang.reflect.Type;

class KVPairAdapter implements JsonDeserializer
{
  public Variables deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
  {
    JsonObject jsonParamMappings = jsonElement.getAsJsonObject();
    Variables mappings = new Variables();
    for (String key : jsonParamMappings.keySet())
    {
      String value = jsonParamMappings.get(key).getAsString();
      mappings.put(key, value);
    }

    return mappings;
  }
}
