package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.gson.game.Creature;

public class DeathCmd extends AbstractCmd
{
  private final Creature target;

  DeathCmd(Creature target)
  {
    this.target = target;
  }

  @Override
  public void execute()
  {
    target.getStats().setState(EState.DEAD);
  }
}
