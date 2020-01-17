package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;

public class ShowHelpCmd extends AbstractCmd
{
  private final int chatId;

  public ShowHelpCmd(int chatId)
  {
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    String helpText = "The following commands are available:\n" +
            "* /new - create a new character\n" +
            "* /help - show this help info\n" +
            "* /me - show your character information.\n" +
            "\n" +
            "Your chat ID is " + chatId;
    SendMessageCmd cmd = new SendMessageCmd(chatId, helpText);
    CommandDelegate.execute(cmd);
  }
}
