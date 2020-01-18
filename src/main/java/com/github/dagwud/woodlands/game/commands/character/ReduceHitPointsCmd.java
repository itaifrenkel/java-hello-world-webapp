package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class ReduceHitPointsCmd extends AbstractCmd
{
  private final GameCharacter character;
  private final int reduceBy;

  public ReduceHitPointsCmd(GameCharacter character, int reduceBy)
  {
    this.character = character;
    this.reduceBy = reduceBy;
  }

  @Override
  public void execute()
  {
    int newHP = character.getStats().getHitPoints() - reduceBy;
    if (newHP < 0)
    {
      newHP = 0;
    }
    character.getStats().setHitPoints(newHP);
  }
}
