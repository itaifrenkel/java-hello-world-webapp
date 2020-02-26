package com.github.dagwud.woodlands.game.commands.start;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.PeriodicSoberUpCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class CreateDefaultSchedulesCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public CreateDefaultSchedulesCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute()
  {
    PeriodicSoberUpCmd periodicSoberUp = new PeriodicSoberUpCmd(character, character.getPlayedBy().getChatId());
    CommandDelegate.execute(periodicSoberUp);
  }
}
