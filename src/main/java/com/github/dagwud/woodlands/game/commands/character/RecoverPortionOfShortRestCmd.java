package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.RecoverHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.RollShortRestCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.math.BigDecimal;

public class RecoverPortionOfShortRestCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter character;
  private final BigDecimal percentOfShortRestToRecover;

  RecoverPortionOfShortRestCmd(PlayerCharacter character, BigDecimal percentOfShortRestToRecover)
  {
    this.character = character;
    this.percentOfShortRestToRecover = percentOfShortRestToRecover;
  }

  @Override
  public void execute()
  {
    RollShortRestCmd roll = new RollShortRestCmd(character, percentOfShortRestToRecover);
    CommandDelegate.execute(roll);
    int hpRecover = roll.getRecoveredHitPoints();

    RecoverHitPointsCmd recover = new RecoverHitPointsCmd(character, hpRecover);
    CommandDelegate.execute(recover);
    CommandDelegate.execute(new SendMessageCmd(character, "<b>You recovered " + "❤" + recover.getHitPointsRecovered() + "</b>"));
  }
}
