package com.github.dagwud.woodlands.game.commands.creatures;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.creatures.CreaturesCacheFactory;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;

public class SpawnCreatureCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int maxDifficulty;
  private Creature spawnedCreature;

  public SpawnCreatureCmd(int maxDifficulty)
  {
    this.maxDifficulty = maxDifficulty;
  }

  @Override
  public void execute()
  {
    Creature template = CreaturesCacheFactory.instance().getCache().pickRandom(0, maxDifficulty);
    Difficulty difficulty = DifficultyCacheFactory.instance().getCache().getDifficulty(template.difficulty);

    Stats stats = new Stats();
    int hitPoints = chooseRandomInRange(difficulty.minimumHitPoints, difficulty.maximumHitPoints);
    stats.setHitPoints(hitPoints);
    stats.setMaxHitPoints(hitPoints);
    stats.setDefenceRating(difficulty.defensiveRating);
    stats.setState(EState.ALIVE);

    spawnedCreature = new Creature(template);
    spawnedCreature.setStats(stats);
  }

  private int chooseRandomInRange(int minInclusive, int maxInclusive)
  {
    int rand = (int) (Math.random() * ((maxInclusive - minInclusive) + 1));
    return rand + minInclusive;
  }

  public Creature getSpawnedCreature()
  {
    return spawnedCreature;
  }
}
