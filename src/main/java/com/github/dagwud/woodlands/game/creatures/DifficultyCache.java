package com.github.dagwud.woodlands.game.creatures;

import com.github.dagwud.woodlands.gson.game.Difficulty;
import com.github.dagwud.woodlands.gson.game.DifficultyRoot;

import java.util.HashMap;
import java.util.Map;

public class DifficultyCache
{
  private final Map<Double, Difficulty> difficulties;

  DifficultyCache(DifficultyRoot root)
  {
    this.difficulties = cacheDifficulties(root);
  }

  private Map<Double, Difficulty> cacheDifficulties(DifficultyRoot root)
  {
    Map<Double, Difficulty> difficulties = new HashMap<>();
    if (root.difficulties != null)
    {
      for (Difficulty difficulty : root.difficulties)
      {
        difficulties.put(difficulty.difficulty, difficulty);
      }
    }
    return difficulties;
  }

  public Difficulty getDifficulty(double difficulty)
  {
    return difficulties.get(difficulty);
  }
}