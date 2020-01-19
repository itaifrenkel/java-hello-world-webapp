package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Weapon;

public interface IFighter
{
  String getName();

  Stats getStats();

  CarriedItems getCarrying();
}
