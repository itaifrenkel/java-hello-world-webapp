package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.PeriodicCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.SoberUpCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class PeriodicSoberUpCmd extends PeriodicCmd<SoberUpCmd>
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public PeriodicSoberUpCmd(PlayerCharacter character, int chatId)
  {
    super(Settings.SOBER_UP_DELAY_MS, new SoberUpCmd(character, chatId));
    this.character = character;
  }

  @Override
  protected boolean shouldCancelPeriodicTimer()
  {
    return character.isDead();
  }

  @Override
  public String toString()
  {
    return "PeriodicSoberUpCmd[character=" + character.getName() + "]";
  }
}
