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

    if (cmd.equals("The Village") || cmd.equals("Village Square"))
    {
      return new GoToLocationInstruction("The Village");
    }
    if (cmd.equals("The Inn"))
    {
      return new GoToLocationInstruction("The Inn");
    }
    if (cmd.equals("The Tavern"))
    {
      return new GoToLocationInstruction("The Tavern");
    }
    if (cmd.equals("Buy Drinks"))
    {
      return new RunProcInstruction("Buy Drinks");
    }
    if (cmd.equals("The Mountain") || cmd.equals("/mountain"))
    {
      return new GoToLocationInstruction("The Mountain");
    }
    if (cmd.equals("The Woodlands") || cmd.equals("/woodlands"))
    {
      return new GoToLocationInstruction("The Woodlands");
    }

    if (telegramUpdate.message.text.equals("/reset"))
    {
//todo nuke this
      return new ResetServerInstruction();
    }
    return new SendMessageInstruction(chatId, "I'm not sure what you mean... perhaps try /help?");
  }
}
