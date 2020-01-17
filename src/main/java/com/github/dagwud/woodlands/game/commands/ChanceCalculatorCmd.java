package com.github.dagwud.woodlands.game.commands;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChanceCalculatorCmd extends AbstractCmd
{
  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
  private final BigDecimal chancePercent;
  private boolean result;

  public ChanceCalculatorCmd(BigDecimal chancePercent)
  {
    this.chancePercent = chancePercent;
  }

  @Override
  public void execute()
  {
    BigDecimal chanceToMeet = determineChanceRatio(chancePercent);
    BigDecimal rolled = new BigDecimal(Math.random());
    result = rolled.compareTo(chanceToMeet) <= 0;
  }

  private BigDecimal determineChanceRatio(BigDecimal percentChance)
  {
    if (percentChance == null)
    {
      return BigDecimal.ONE;
    }
    return percentChance.divide(ONE_HUNDRED, RoundingMode.HALF_UP);
  }

  public boolean getResult()
  {
    return result;
  }
}
