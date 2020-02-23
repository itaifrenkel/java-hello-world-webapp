package com.github.dagwud.woodlands.game.log;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;

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

  public static void info(String message)
  {
    getInstance().logs.add(message);
    System.out.println(message);
    while (getInstance().logs.size() > MAX_LOG)
    {
      getInstance().logs.remove(0);
    }
  }

  public static void logError(Exception e)
  {
    e.printStackTrace();
    try (ByteArrayOutputStream out = new ByteArrayOutputStream())
    {
      try (PrintStream ps = new PrintStream(out))
      {
        e.printStackTrace(ps);
        String log = out.toString();
        getInstance().logs.add(log);
        sendAdminMessage(log);
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
    sendAdminMessage(message);
  }

  private static void sendAdminMessage(String out)
  {
    try
    {
      CommandDelegate.execute(new SendMessageCmd(Settings.ADMIN_CHAT, out));
    }
    catch (Exception ignore)
    {
      // oh well
    }
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
