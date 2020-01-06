package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.GameState;
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

    if (cmd.equals("/new"))
    {
      return new CreateCharacterInstruction(chatId);
    }
    if (cmd.equals("/help"))
    {
      return new ShowHelpInstruction();
    }
    if (cmd.equals("/village"))
    {
      return new GoToVillageInstruction();
    }
    if (cmd.equals("/mountain"))
    {
      return new GoToMountainsInstruction();
    }
    if (cmd.equals("/woodlands"))
    {
      return new GoToWoodlandsInstruction();
    }

    if (telegramUpdate.message.text.equals("/reset"))
    {
//todo nuke this
      return new ResetServerInstruction();
    }
    return new SendMessageInstruction(chatId, "I'm not sure what you mean... perhaps try /help?");
  }
}
