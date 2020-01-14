package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionsCacheFactory;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
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

  public GameInstruction create(Update telegramUpdate)
  {
    int chatId = telegramUpdate.message.chat.id;
    String cmd = telegramUpdate.message.text;

    if (ActionsCacheFactory.instance().isQuickCommand(cmd))
    {
      QuickCommand quickCommand = ActionsCacheFactory.instance().findQuickCommand(cmd);
      return new RunProcInstruction(quickCommand.procName, quickCommand.paramMappings == null ? new Variables() : quickCommand.paramMappings);
    }

    if (cmd.equals("Buy Drinks"))
    {
      return new RunProcInstruction("Buy Drinks");
    }

    return new SendMessageInstruction(chatId, "I'm not sure what you mean... perhaps try /help?");
  }
}
