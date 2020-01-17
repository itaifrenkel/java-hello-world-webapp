package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.domain.GameCharacter;

class ReducePlayerHealthCmd extends AbstractCmd
{
  private final GameCharacter character;
  private final int reduceBy;

  ReducePlayerHealthCmd(GameCharacter character, int reduceBy)
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
