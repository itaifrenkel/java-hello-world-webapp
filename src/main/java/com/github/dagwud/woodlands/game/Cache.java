package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.domain.GameObject;

import java.util.HashMap;
import java.util.Map;

public abstract class Cache
{
  protected <T extends GameObject> Map<String, T> cache(T[] cacheFrom)
  {
    Map<String, T> cacheTo = new HashMap<>();
    if (cacheFrom != null)
    {
      for (T object : cacheFrom)
      {
        cacheTo.put(object.getName(), object);
      }
    }
    return cacheTo;
  }

}
