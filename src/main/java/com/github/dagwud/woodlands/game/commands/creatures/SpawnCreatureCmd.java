package com.github.dagwud.woodlands.game.commands.creatures;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.creatures.CreaturesCacheFactory;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;

public class SpawnCreatureCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final double minDifficulty;
  private final double maxDifficulty;
  private final String creatureType;
  private Creature spawnedCreature;

  public SpawnCreatureCmd(double minDifficulty, double maxDifficulty, String creatureType)
  {
    this.minDifficulty = minDifficulty;
    this.maxDifficulty = maxDifficulty;
    this.creatureType = creatureType;
  }

  @Override
  public void execute()
  {
    Creature template = chooseCreature();
    Difficulty difficulty = DifficultyCacheFactory.instance().getCache().getDifficulty(template.difficulty);

    Stats stats = new Stats();
    int hitPoints = chooseRandomInRange(difficulty.minimumHitPoints, difficulty.maximumHitPoints);
    stats.setHitPoints(hitPoints);
    stats.getMaxHitPoints().setBase(hitPoints);
    stats.setStrength(16, 0);
    stats.setAgility(16, 0);
    stats.setDefenceRatingBoost(difficulty.defensiveRating);
    stats.setState(EState.ALIVE);

    spawnedCreature = new Creature(template);
    spawnedCreature.setStats(stats);
  }

  private Creature chooseCreature()
  {
    Creature template = null;
    int attempts = 0;
    while (template == null)
    {
      template = CreaturesCacheFactory.instance().getCache().pickRandom(minDifficulty, maxDifficulty);
      if (creatureType != null && !creatureType.equalsIgnoreCase(template.creatureType))
      {
        if (attempts >= 100)
        {
          SendAdminMessageCmd err = new SendAdminMessageCmd("Unable to find any creatures of type '" + creatureType + "'");
          CommandDelegate.execute(err);
          return template;
        }
        template = null; // try again
        attempts++;
      }
    }
    return template;
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
