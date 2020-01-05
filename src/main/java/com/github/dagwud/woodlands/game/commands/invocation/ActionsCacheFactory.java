package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.ActionsCache;
import com.github.dagwud.woodlands.gson.game.Root;
import com.github.dagwud.woodlands.gson.adapter.GsonHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ActionsCacheFactory
{
  private static ActionsCacheFactory instance;
  private final ActionsCache actions;

  private ActionsCacheFactory()
  {
    String json = readFile();
    Root root = GsonHelper.readJSON(json, Root.class);
    actions = new ActionsCache(root);
  }

  private String readFile()
  {
    Path path = new File("src/main/resources/test.json").toPath();
    byte[] bytes;
    try
    {
      bytes = Files.readAllBytes(path);
    }
    catch (IOException e)
    {
      throw new RuntimeException("Unable to initialize", e);
    }
    return new String(bytes);
  }

  public static ActionsCacheFactory instance()
  {
    if (null == instance)
    {
      createInstance();
    }
    return instance;
  }

  private static synchronized void createInstance()
  {
    if (null == instance)
    {
      instance = new ActionsCacheFactory();
    }
  }

  public ActionsCache getActions()
  {
    return actions;
  }
}
