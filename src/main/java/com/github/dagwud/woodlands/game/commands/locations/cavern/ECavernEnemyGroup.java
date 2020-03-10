package com.github.dagwud.woodlands.game.commands.locations.cavern;

enum ECavernEnemyGroup
{
  CAVERN_1(5, 4, 4),
  CAVERN_2(6, 5, 5),
  CAVERN_3(6, 6, 6),
  CAVERN_4(6, 7, 7),
  CAVERN_5(5, 9, 9),
  CAVERN_6(4, 10, 10),
  CAVERN_7(5, 11, 11),
  CAVERN_8(5, 12, 12);

  private static final long serialVersionUID = 1L;

  public final int enemyCount;
  public final double minDifficulty;
  public final double maxDifficulty;

  ECavernEnemyGroup(int enemyCount, double minDifficulty, double maxDifficulty)
  {
    this.enemyCount = enemyCount;
    this.minDifficulty = minDifficulty;
    this.maxDifficulty = maxDifficulty;
  }
}
