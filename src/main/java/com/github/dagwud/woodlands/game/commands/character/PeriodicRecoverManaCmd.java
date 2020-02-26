package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.PeriodicCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class PeriodicRecoverManaCmd extends PeriodicCmd<DoPeriodicRecoverManaCmd>
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public PeriodicRecoverManaCmd(PlayerCharacter character)
  {
    super(Settings.DELAY_BETWEEN_MANA_RECOVERY);
    this.character = character;
  }

  @Override
  protected boolean shouldRunSingle()
  {
    return character.isConscious();
  }

  @Override
  public DoPeriodicRecoverManaCmd createSingleRunCmd()
  {
    return new DoPeriodicRecoverManaCmd(character, 1);
  }

  @Override
  protected boolean shouldCancelPeriodicTimer()
  {
    return character.isDead();
  }

}
