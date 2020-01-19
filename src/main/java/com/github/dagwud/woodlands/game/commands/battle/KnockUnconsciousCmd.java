package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.gson.game.Creature;

public class KnockUnconsciousCmd extends AbstractCmd
{
  private final Creature target;

  KnockUnconsciousCmd(Creature target)
  {
    this.target = target;
  }

  @Override
  public void execute()
  {
  }
}
