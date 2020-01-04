package com.github.dagwud.woodlands.gson.game;

import java.util.HashMap;
import java.util.Map;

public class ParamMappings
{
  public Map<String, String> mappings;

  public ParamMappings()
  {
    mappings = new HashMap<>();
  }

  @Override
  public String toString()
  {
    return "ParamMappings{" +
            "mappings=" + mappings +
            '}';
  }
}
