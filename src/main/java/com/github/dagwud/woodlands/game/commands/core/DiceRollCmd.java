package com.github.dagwud.woodlands.game.commands.core;

public class DiceRollCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final int diceCount;
  private final int diceFaces;
  private int total;

  public DiceRollCmd(int diceCount, int diceFaces)
  {
    this.diceCount = diceCount;
    this.diceFaces = diceFaces;
  }

  @Override
  public void execute()
  {
    total = 0;
    for (int i = 0; i < diceCount; i++)
    {
      int diceRoll = (int) (Math.random() * diceFaces) + 1;
      total += diceRoll;
    }
  }

  public int getTotal()
  {
    return total;
  }
}
