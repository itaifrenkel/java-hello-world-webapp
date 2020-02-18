package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.SoberUpCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class PeriodicSoberUpCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;
  private final int chatId;

  public PeriodicSoberUpCmd(PlayerCharacter character, int chatId)
  {
    this.character = character;
    this.chatId = chatId;
  }

  @Override
  public void execute()
  {
    SoberUpCmd cmd = new SoberUpCmd(character, chatId);
    CommandDelegate.execute(cmd);

    if (!character.isDead())
    {
      PeriodicSoberUpCmd periodicSoberUp = new PeriodicSoberUpCmd(character, chatId);
      RunLaterCmd next = new RunLaterCmd(Settings.SOBER_UP_DELAY_MS, periodicSoberUp);
      CommandDelegate.execute(next);
    }
  }

  @Override
  public String toString()
  {
    return "PeriodicSoberUpCmd[character=" + character.getName() + "]";
  }
}
