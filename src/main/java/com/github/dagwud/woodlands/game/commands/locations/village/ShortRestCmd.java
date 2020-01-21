package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class ShortRestCmd extends AbstractCmd
{
  private final int chatId;
  private final GameCharacter activeCharacter;

  public ShortRestCmd(int chatId, GameCharacter activeCharacter)
  {
    super(new AbleToActPrerequisite(activeCharacter));
    this.chatId = chatId;
    this.activeCharacter = activeCharacter;
  }

  @Override
  public void execute()
  {
    activeCharacter.getStats().setState(EState.RESTING);
    AbstractCmd restCompletedCmd = new DoShortRestCmd(chatId, activeCharacter);
    restCompletedCmd = new RunLaterCmd(10000, restCompletedCmd);
    CommandDelegate.execute(restCompletedCmd);

    SendMessageCmd echo = new SendMessageCmd(chatId, "You're resting.");
    CommandDelegate.execute(echo);
  }
}
