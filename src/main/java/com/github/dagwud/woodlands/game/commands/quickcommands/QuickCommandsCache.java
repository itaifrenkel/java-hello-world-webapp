package com.github.dagwud.woodlands.game.commands.quickcommands;

import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.gson.game.QuickCommand;
import com.github.dagwud.woodlands.gson.game.Root;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QuickCommandsCache
{
  private final Map<String, QuickCommand> quickCommands;

  public QuickCommandsCache(Collection<Root> roots)
  {
    this.quickCommands = new HashMap<>();
    for (Root root : roots)
    {
      for (QuickCommand quickCommand : root.quickCommands)
      {
        for (String command : quickCommand.command)
        {
          quickCommands.put(command, quickCommand);
        }
      }
    }
  }

  public boolean isQuickCommand(String command)
  {
    return quickCommands.containsKey(command);
  }

  public QuickCommand findQuickCommand(String command)
  {
    QuickCommand found = quickCommands.get(command);
    if (null == found)
    {
      throw new WoodlandsRuntimeException("No quick command '" + command + "' exists");
    }
    return found;
  }
}
