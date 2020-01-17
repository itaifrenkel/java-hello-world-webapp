package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.CommandDelegate;

public class ShowMenuCmd extends AbstractCmd
{
  private final int chatId;
  private final String message;
  private final String[] options;

  ShowMenuCmd(int chatId, String message, String[] options)
  {
    this.chatId = chatId;
    this.message = message;
    this.options = options;
  }

  @Override
  public void execute()
  {
    ChoiceCmd cmd = new ChoiceCmd(chatId, message, options);
    CommandDelegate.execute(cmd);
  }
}
