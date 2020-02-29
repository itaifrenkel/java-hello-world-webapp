package com.github.dagwud.woodlands.game.commands.admin;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.admin.AdminCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.creatures.CreaturesCacheFactory;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;
import com.github.dagwud.woodlands.gson.game.Item;

public class ListCreaturesCmd extends AdminCmd
{
  private static final long serialVersionUID = 1L;

  public ListCreaturesCmd(int chatId)
  {
    super(chatId);
  }

  @Override
  public void execute()
  {
    for (Creature template : CreaturesCacheFactory.instance().getCache().listAll())
    {
      // todo duplication in SpawnCreatureCmd:
      Difficulty difficulty = DifficultyCacheFactory.instance().getCache().getDifficulty(template.difficulty);

      Stats stats = new Stats();
      int hitPoints = chooseRandomInRange(difficulty.minimumHitPoints, difficulty.maximumHitPoints);
      stats.setHitPoints(hitPoints);
      stats.getMaxHitPoints().setBase(hitPoints);
      stats.setStrength(16, 0);
      stats.setAgility(16, 0);
      stats.setDefenceRatingBoost(difficulty.defensiveRating);
   
      Creature spawnedCreature = new Creature(template);
      spawnedCreature.setStats(stats);

      String desc = "L" + spawnedCreature.difficulty + ": " + spawnedCreature.summary();
      
      String message = desc;
      // todo duplicate in GenerateEncounterCmd:
      Item carriedLeft = spawnedCreature.getCarrying().getCarriedLeft();
      Item carriedRight = spawnedCreature.getCarrying().getCarriedRight();
      if (carriedLeft != null || carriedRight != null)
      {
        if (carriedLeft != null)
        {
          message += ", ";
          message += carriedLeft.summary(spawnedCreature, false);
        }
        if (carriedRight != null)
        {
          message += ", ";
          message += carriedRight.summary(spawnedCreature, false);
        }
      }

      CommandDelegate.execute(new SendAdminMessageCmd(desc));
    }
  }

  private int chooseRandomInRange(int minInclusive, int maxInclusive)
  {
    int rand = (int) (Math.random() * ((maxInclusive - minInclusive) + 1));
    return rand + minInclusive;
  }

}
