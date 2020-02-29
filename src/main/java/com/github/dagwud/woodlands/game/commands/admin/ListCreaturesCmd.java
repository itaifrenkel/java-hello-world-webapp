package com.github.dagwud.woodlands.game.commands.creatures;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AdminCmd;
import com.github.dagwud.woodlands.game.commands.core.SendAdminMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.creatures.CreaturesCacheFactory;
import com.github.dagwud.woodlands.game.creatures.DifficultyCacheFactory;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Difficulty;

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
      // todo duplication
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

      String desc = spawnedCreature.name + " (L" + spawnedCreature.difficulty + "): " + spawnedCreature.summary();
      CommandDelegate.execute(new SendAdminCmd("• " + desc));
    }
  }
}
