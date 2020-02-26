package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.RecoverManaCmd;
import com.github.dagwud.woodlands.game.commands.core.PeriodicCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class PeriodicRecoverManaCmd extends PeriodicCmd<RecoverManaCmd>
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public PeriodicRecoverManaCmd(PlayerCharacter character)
  {
    super(Settings.DELAY_BETWEEN_MANA_RECOVERY, new RecoverManaCmd(character, 1));
    this.character = character;
  }

  @Override
  protected boolean shouldCancelPeriodicTimer()
  {
    return character.isDead();
  }
}
