package com.github.dagwud.woodlands.gson.game;

import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.stats.Stats;

public class Creature
{
  public String name;
  public int difficulty;
  private Stats stats;

  public Creature()
  {
  }

  public Creature(Creature template)
  {
    this();
    this.name = template.name;
    this.difficulty = template.difficulty;
  }

  public Stats getStats()
  {
    return stats;
  }

  public void setStats(Stats stats)
  {
    this.stats = stats;
  }
}
