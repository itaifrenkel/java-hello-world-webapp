package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class ShortRestCmd extends RestCmd
{
  private static final long serialVersionUID = 1L;

  public ShortRestCmd(int chatId, PlayerCharacter character)
  {
    super(chatId, character);
  }

  @Override
  public void execute()
  {
    if (getCharacter().getStats().getRestPoints() <= 0)
    {
      SendMessageCmd cmd = new SendMessageCmd(getCharacter().getPlayedBy().getChatId(), "Your wounds are starting to take their toll; you need a full rest to fully recover");
      CommandDelegate.execute(cmd);
      return;
    }

    if (isFullyRested(getCharacter()))
    {
      SendMessageCmd cmd = new SendMessageCmd(getChatId(), "You have initiated a short rest for your party");
      CommandDelegate.execute(cmd);
    }

    for (PlayerCharacter member : getCharacter().getParty().getActivePlayerCharacters())
    {
      Stats stats = member.getStats();
      if (!member.isDead() && member.getStats().getState() != EState.RESTING && !isFullyRested(member))
      {
        if (stats.getRestPoints() > 0)
        {
          scheduleRest(member);
        }
      }
    }
  }

  @Override
  void scheduleRest(PlayerCharacter restFor)
  {
    Stats stats = restFor.getStats();
    stats.setState(EState.RESTING);
    AbstractCmd restCompletedCmd = new DoRestCmd(restFor.getPlayedBy().getChatId(), restFor, false);
    restCompletedCmd = new RunLaterCmd(Settings.SHORT_REST_DURATION_MS, restCompletedCmd);
    CommandDelegate.execute(restCompletedCmd);
    SendMessageCmd echo = new SendMessageCmd(restFor.getPlayedBy().getChatId(), "You're resting" + (restFor != getCharacter() ? " (initiated by " + getCharacter().getName() + ")" : ""));
    CommandDelegate.execute(echo);
  }
}
