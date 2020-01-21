package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class SoberUpCmd extends AbstractCmd
{
  public static final long SOBER_UP_DELAY_MS = 30_000;

  private final GameCharacter activeCharacter;
  private final int chatId;

  public SoberUpCmd(GameCharacter activeCharacter, int chatId)
  {
    this.activeCharacter = activeCharacter;
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    Stats stats = activeCharacter.getStats();
    if (stats.getDrunkeness() != 0)
    {
      CommandDelegate.execute(new SendMessageCmd(chatId, "You sober up a bit."));
      stats.setDrunkeness(Math.max(0, stats.getDrunkeness() - 1));
    }

    RunLaterCmd nextSoberUp = new RunLaterCmd(SOBER_UP_DELAY_MS, new SoberUpCmd(activeCharacter, chatId));
    CommandDelegate.execute(nextSoberUp);
  }
}
