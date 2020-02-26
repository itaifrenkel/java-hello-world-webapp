package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.PeriodicCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class PeriodicRecoverHPCmd extends PeriodicCmd<RecoverPortionOfShortRestCmd>
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;

  public PeriodicRecoverHPCmd(PlayerCharacter character)
  {
    super(Settings.DELAY_BETWEEN_HP_RECOVERY);
    this.character = character;
  }

  @Override
  protected boolean shouldRunSingle()
  {
    return character.isConscious();
  }

  @Override
  public RecoverPortionOfShortRestCmd createSingleRunCmd()
  {
    return new RecoverPortionOfShortRestCmd(character, Settings.SCHEDULED_HP_RECOVERY_PERCENT_OF_SHORT_REST);
  }

  @Override
  protected boolean shouldCancelPeriodicTimer()
  {
    return character.isDead() || !character.isActive();
  }

}
