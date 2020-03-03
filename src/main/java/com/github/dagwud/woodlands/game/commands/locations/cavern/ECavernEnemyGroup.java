package com.github.dagwud.woodlands.game.commands.locations.cavern;

enum ECavernEnemyGroup
{
  CAVERN_1(2, 1, 2),
  CAVERN_2(2, 2, 3),
  CAVERN_3(2, 3, 4),
  CAVERN_4(3, 4, 5),
  CAVERN_5(3, 5, 7),
  CAVERN_6(4, 5, 7),
  CAVERN_7(5, 7, 9),
  CAVERN_8(5, 10, 12);

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
