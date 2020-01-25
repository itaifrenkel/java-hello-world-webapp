package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.stats.Stats;

public abstract class Fighter
{
  public abstract String getName();

  public abstract Stats getStats();

  public abstract CarriedItems getCarrying();

  public String summary()
  {
    Stats stats = getStats();
    if (stats.getState() == EState.DEAD)
    {
      return getName() + ": ☠️dead";
    }
    String message = getName() + ": ❤" + stats.getHitPoints() + " / " + stats.getMaxHitPoints();
    if (stats.getMaxMana() != 0)
    {
      message += ", ✨" + stats.getMana() + "/" + stats.getMaxMana();
    }
    return message;
  }
}
