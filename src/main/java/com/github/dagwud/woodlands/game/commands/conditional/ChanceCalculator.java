package com.github.dagwud.woodlands.game.commands.conditional;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChanceCalculator
{
  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

  private ChanceCalculator()
  {
  }

  public static BigDecimal determineChanceRatio(String chance)
  {
    if (chance == null)
    {
      return BigDecimal.ONE;
    }
    String c = chance.trim();
    if (c.endsWith("%"))
    {
      return new BigDecimal(c.substring(0, c.length() - 1)).divide(ONE_HUNDRED, RoundingMode.HALF_UP);
    }
    throw new IllegalArgumentException("Unrecognizable chance format: \"" + c + "\"");
  }

}
