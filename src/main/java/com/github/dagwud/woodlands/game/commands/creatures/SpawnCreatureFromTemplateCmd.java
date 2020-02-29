package com.github.dagwud.woodlands.game.commands.creatures;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;

public class SpawnCreatureFromTemplateCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Creature template;
  private Creature spawnedCreature;

  public SpawnCreatureFromTemplateCmd(Creature template)
  {
    this.template = template;
  }

  @Override
  public void execute()
  {
    spawnedCreature = new Creature(template);
    Stats stats = buildStats(template);
    spawnedCreature.setStats(stats);
  }

  private Stats buildStats(Creature template)
  {
    Difficulty difficulty = DifficultyCacheFactory.instance().getCache().getDifficulty(template.difficulty);

    Stats stats = new Stats();
    int hitPoints = chooseRandomInRange(difficulty.minimumHitPoints, difficulty.maximumHitPoints);
    stats.setHitPoints(hitPoints);
    stats.getMaxHitPoints().setBase(hitPoints);
    stats.setStrength(16, 0);
    stats.setAgility(16, 0);
    stats.setDefenceRatingBoost(difficulty.defensiveRating);
    stats.setState(EState.ALIVE);

    return stats;
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
