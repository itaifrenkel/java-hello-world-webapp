package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.SoberUpCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class PeriodicSoberUpCmd extends AbstractCmd
{
  public static final long SOBER_UP_DELAY_MS = 30_000;

  private final GameCharacter character;
  private final int chatId;

  PeriodicSoberUpCmd(GameCharacter character, int chatId)
  {
    this.character = character;
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    SoberUpCmd cmd = new SoberUpCmd(character, chatId);
    CommandDelegate.execute(cmd);

    PeriodicSoberUpCmd periodicSoberUp = new PeriodicSoberUpCmd(character, chatId);
    RunLaterCmd next = new RunLaterCmd(SOBER_UP_DELAY_MS, periodicSoberUp);
    CommandDelegate.execute(next);
  }
}
