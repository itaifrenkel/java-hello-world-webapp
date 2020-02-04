package com.github.dagwud.woodlands.game.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
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

  public static void info(String message)
  {
    getInstance().logs.add(message);
    System.out.println(message);
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

  public static void logError(Exception e)
  {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream())
    {
      try (PrintStream ps = new PrintStream(out))
      {
        e.printStackTrace(ps);
        getInstance().logs.add(out.toString());
      }
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }

  public static void error(String message)
  {
    info("*" + message + "*");
  }

  public static List<String> getLogs()
  {
    return Collections.unmodifiableList(getInstance().logs);
  }

  public static void clear()
  {
    getInstance().logs.clear();
  }
}
