package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Fighter;

public class KnockUnconsciousCmd extends AbstractCmd
{
  private final Fighter target;

  public KnockUnconsciousCmd(Fighter target)
  {
    this.target = target;
  }

  @Override
  public void execute()
  {
    target.getStats().setState(EState.UNCONSCIOUS);
  }
}
