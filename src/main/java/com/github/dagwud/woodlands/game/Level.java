package com.github.dagwud.woodlands.game;

public class Level
{
  private int level;
  private int experience;

  public Level(int level, int experience)
  {
    this.level = level;
    this.experience = experience;
  }

  public int getLevel()
  {
    return level;
  }

  public int getExperience()
  {
    return experience;
  }
}
