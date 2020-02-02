package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class ShortRestCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int chatId;
  private final PlayerCharacter character;

  public ShortRestCmd(int chatId, PlayerCharacter character)
  {
    super(new AbleToActPrerequisite(character));
    this.chatId = chatId;
    this.character = character;
  }

  @Override
  public void execute()
  {
    Stats stats = character.getStats();
    if (stats.getRestPoints() <= 0)
    {
      SendMessageCmd cmd = new SendMessageCmd(character.getPlayedBy().getChatId(), "You need a full rest\nThis is not yet implemented, so for now we'll let it slide");
      CommandDelegate.execute(cmd);
      stats.setRestPoints(1); //todo because long rest not yet implemented - needs to be removed, and at this point should abort the short rest
    }

    for (GameCharacter member : character.getParty().getActivePlayerCharacters())
    {
      if (member.getStats().getHitPoints() < member.getStats().getMaxHitPoints())
      {
        stats.setState(EState.RESTING);
        AbstractCmd restCompletedCmd = new DoShortRestCmd(chatId, character);
        restCompletedCmd = new RunLaterCmd(10000, restCompletedCmd);
        CommandDelegate.execute(restCompletedCmd);
        SendMessageCmd echo = new SendMessageCmd(chatId, "You're resting" + (member != character ? " (initiated by " + character.getName() + ")" : ""));
        CommandDelegate.execute(echo);
      }
    }
  }
}
