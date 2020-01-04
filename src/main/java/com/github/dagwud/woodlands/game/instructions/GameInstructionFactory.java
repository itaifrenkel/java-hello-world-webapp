package com.github.dagwud.woodlands.game.instructions;

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
      instance = new GameInstructionFactory();
    }
  }

  public GameInstruction create(Update telegramUpdate)
  {
    int chatId = telegramUpdate.message.chat.id;
    if (telegramUpdate.message.text.equals("/new"))
    {
      return new CreateCharacterInstruction(chatId);
    }
    return new SendMessageInstruction(chatId, "I'm not sure what you mean... perhaps try `/help`?");
  }
}
