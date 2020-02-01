package com.github.dagwud.woodlands.game.log;

import java.util.ArrayList;
import java.util.List;

public class Logger
{
  private static final int MAX_LOG = 200;

  private static Logger instance;

  private List<String> logs;

  private Logger()
  {
    logs = new ArrayList<>();
  }

  public static void log(String message)
  {
    getInstance().logs.add(message);
    while (getInstance().logs.size() > MAX_LOG)
    {
      getInstance().logs.remove(0);
    }
  }

  private static Logger getInstance()
  {
    if (instance == null)
    {
      createInstance();
    }
    return instance;
  }

  private synchronized static void createInstance()
  {
    if (instance != null)
    {
      return;
    }
    instance = new Logger();
  }
}
