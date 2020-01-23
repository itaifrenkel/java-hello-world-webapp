package com.github.dagwud.woodlands.game.creatures;

import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.CreaturesRoot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CreaturesCache
{
  private final Map<String, Creature> creatures;

  CreaturesCache(CreaturesRoot root)
  {
    this.creatures = cacheCreatures(root);
  }

  private Map<String, Creature> cacheCreatures(CreaturesRoot root)
  {
    Map<String, Creature> creatures = new HashMap<>();
    if (root.creatures != null)
    {
      for (Creature creature : root.creatures)
      {
        creatures.put(creature.name, creature);
      }
    }
    return creatures;
  }

  public Creature pickRandom(int minDifficulty, int maxDifficulty)
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