package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class LongRestCmd extends RestCmd
{
  private static final long serialVersionUID = 1L;

  public LongRestCmd(int chatId, PlayerCharacter character)
  {
    super(chatId, character);
  }

  @Override
  public void execute()
  {
    if (isFullyRested(getCharacter()))
    {
      SendMessageCmd cmd = new SendMessageCmd(getChatId(), "There are things to see and creatures to hunt! You don't need to rest.");
      CommandDelegate.execute(cmd);
      return;
    }

    scheduleRest(getCharacter());
  }

  @Override
  void scheduleRest(PlayerCharacter restFor)
  {
    Stats stats = restFor.getStats();
    stats.setState(EState.LONG_RESTING);
    AbstractCmd restCompletedCmd = new DoRestCmd(restFor.getPlayedBy().getChatId(), restFor, true);
    restCompletedCmd = new RunLaterCmd(Settings.LONG_REST_DURATION_MS, restCompletedCmd);
    CommandDelegate.execute(restCompletedCmd);
    SendMessageCmd echo = new SendMessageCmd(restFor.getPlayedBy().getChatId(), "You're resting");
    CommandDelegate.execute(echo);
  }
}
