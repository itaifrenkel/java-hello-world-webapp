package com.github.dagwud.woodlands.game.commands;

public class DiceRollCmd extends AbstractCmd
{
  private final int diceCount;
  private final int diceFaces;
  private int total;

  DiceRollCmd(int diceCount, int diceFaces)
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
    System.out.println("ROLL: " + diceCount + "d" + diceFaces + "=" + total);
  }

  int getTotal()
  {
    return total;
  }
}
