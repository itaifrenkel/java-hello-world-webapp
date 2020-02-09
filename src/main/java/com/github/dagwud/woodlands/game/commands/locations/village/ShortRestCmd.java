package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
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
    if (character.getStats().getRestPoints() <= 0)
    {
      SendMessageCmd cmd = new SendMessageCmd(character.getPlayedBy().getChatId(), "You need a full rest\nThis is not yet implemented, so for now we'll let it slide. You've been granted a free bonus short rest. You can use it now");
      CommandDelegate.execute(cmd);
      character.getStats().setRestPoints(1); //todo because long rest not yet implemented - needs to be removed, and at this point should abort the short rest
      return;
    }

    if (character.getStats().getHitPoints() == character.getStats().getMaxHitPoints() && character.getStats().getMana() == character.getStats().getMaxMana())
    {
      SendMessageCmd cmd = new SendMessageCmd(chatId, "You have initiated a short rest for your party");
      CommandDelegate.execute(cmd);
    }

    for (PlayerCharacter member : character.getParty().getActivePlayerCharacters())
    {
      Stats stats = member.getStats();
      if (stats.getHitPoints() < stats.getMaxHitPoints() || stats.getMana().total() < stats.getMaxMana().total())
      {
        if (stats.getRestPoints() > 0 || true /*todo see above*/)
        {
          stats.setState(EState.RESTING);
          AbstractCmd restCompletedCmd = new DoShortRestCmd(member.getPlayedBy().getChatId(), member);
          restCompletedCmd = new RunLaterCmd(Settings.SHORT_REST_DURATION_MS, restCompletedCmd);
          CommandDelegate.execute(restCompletedCmd);
          SendMessageCmd echo = new SendMessageCmd(member.getPlayedBy().getChatId(), "You're resting" + (member != character ? " (initiated by " + character.getName() + ")" : ""));
          CommandDelegate.execute(echo);
        }
      }
    }
  }
}
