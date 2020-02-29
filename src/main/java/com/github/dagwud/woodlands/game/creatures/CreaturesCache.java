package com.github.dagwud.woodlands.game.creatures;

import com.github.dagwud.woodlands.game.Cache;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.CreaturesRoot;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class CreaturesCache extends Cache
{
  private final Map<String, Creature> creatures;

  CreaturesCache(CreaturesRoot root)
  {
    this.creatures = cache(root.creatures);
  }

  public Collection<Creature> listAll()
  {
    return creatures.values();
  }

  public Creature pickRandom(double minDifficulty, double maxDifficulty)
  {
    Creature picked = null;
    while (picked == null)
    {
      int index = (int) (Math.random() * creatures.size());
      Iterator<Creature> it = creatures.values().iterator();
      for (int i = 0; i < index; i++)
      {
        it.next();
      }
      picked = it.next();
      if (picked.difficulty > maxDifficulty || picked.difficulty < minDifficulty)
      {
        picked = null; // pick again
      }
    }
    return picked;
  }
}
