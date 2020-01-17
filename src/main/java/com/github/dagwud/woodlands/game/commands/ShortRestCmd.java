package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class ShortRestCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter activeCharacter;

  public ShortRestCmd(int chatId, GameCharacter activeCharacter)
  {
    this.chatId = chatId;
    this.activeCharacter = activeCharacter;
  }

  @Override
  public void execute()
  {
    AbstractCmd restCompletedCmd = new DoShortRestCmd(chatId, activeCharacter);
    if (chatId != -1)
    {
      restCompletedCmd = new RunLaterCmd(10000, restCompletedCmd);
    }
    CommandDelegate.execute(restCompletedCmd);

    SendMessageCmd echo = new SendMessageCmd(chatId, "You're resting.");
    CommandDelegate.execute(echo);
  }
}
