package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Fighter;

public class RecoverHitPointsCmd extends AbstractCmd
{
  private final Fighter character;
  private final int hitPointsRecovered;

  public RecoverHitPointsCmd(Fighter character, int hitPointsRecovered)
  {
    this.character = character;
    this.hitPointsRecovered = hitPointsRecovered;
  }

  @Override
  public void execute()
  {
    character.getStats().setHitPoints(character.getStats().getHitPoints() + hitPointsRecovered);
    if (character.getStats().getHitPoints() > 0)
    {
      character.getStats().setState(EState.ALIVE);
    }
  }
}
