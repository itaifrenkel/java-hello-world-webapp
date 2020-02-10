package com.github.dagwud.woodlands.game;

import java.util.ArrayList;
import java.util.List;

public class Levels
{
  private static final List<Level> LEVELS = new ArrayList<Level>()
  {{
    add(new Level(20, 355000));
    add(new Level(19, 305000));
    add(new Level(18, 265000));
    add(new Level(17, 225000));
    add(new Level(16, 195000));
    add(new Level(15, 165000));
    add(new Level(14, 140000));
    add(new Level(13, 120000));
    add(new Level(12, 100000));
    add(new Level(11, 85000));
    add(new Level(10, 64000));
    add(new Level(9, 48000));
    add(new Level(8, 34000));
    add(new Level(7, 23000));
    add(new Level(6, 14000));
    add(new Level(5, 6500));
    add(new Level(4, 2700));
    add(new Level(3, 900));
    add(new Level(2, 300));
    add(new Level(1, 0));
  }};

  private Levels() {}

  public static int determineExperience(int level)
  {
    for (Level levelCombo : LEVELS)
    {
      if (level == levelCombo.getLevel())
      {
        return levelCombo.getExperience();
      }
    }

    // we need more levels at this point
    return Integer.MAX_VALUE;
  }

  public static int determineLevel(int experience)
  {
    for (Level level : LEVELS)
    {
      if (experience >= level.getExperience())
      {
        return level.getLevel();
      }
    }

    // we need more levels at this point
    return Integer.MAX_VALUE;
  }
}
