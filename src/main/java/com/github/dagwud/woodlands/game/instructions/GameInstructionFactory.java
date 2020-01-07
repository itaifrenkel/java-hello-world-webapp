package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionsCacheFactory;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.quickcommands.QuickCommandsCache;
import com.github.dagwud.woodlands.gson.game.Action;
import com.github.dagwud.woodlands.gson.game.QuickCommand;
import com.github.dagwud.woodlands.gson.telegram.Update;

public class GameInstructionFactory
{
  private static GameInstructionFactory instance;

  public static GameInstructionFactory instance()
  {
    if (null == instance)
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
    instance = new GameInstructionFactory();
  }

  public GameInstruction create(Update telegramUpdate, GameState gameState)
  {
    int chatId = telegramUpdate.message.chat.id;
    String cmd = telegramUpdate.message.text;

    if (ActionsCacheFactory.instance().getQuickCommands().isQuickCommand(cmd))
    {
      QuickCommand quickCommand = ActionsCacheFactory.instance().getQuickCommands().findQuickCommand(cmd);
      return new RunProcInstruction(quickCommand.procName, quickCommand.paramMappings == null ? new Variables() : quickCommand.paramMappings, new Variables());
    }

    if (cmd.equals("Buy Drinks"))
    {
      return new RunProcInstruction("Buy Drinks");
    }

    return new SendMessageInstruction(chatId, "I'm not sure what you mean... perhaps try /help?");
  }
}
