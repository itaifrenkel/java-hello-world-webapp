package com.github.dagwud.woodlands.game.commands.conditional;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChanceCalculator extends ConditionEvaluator
{
  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

  ChanceCalculator(String expression)
  {
    super(expression);
  }

  @Override
  public boolean evaluatesToTrue()
  {
    String chance = expression.substring("chance(".length());
    chance = chance.substring(0, chance.length() - ")".length());
    BigDecimal chanceToMeet = determineChanceRatio(chance);
    BigDecimal rolled = new BigDecimal(Math.random());
    return rolled.compareTo(chanceToMeet) <= 0;
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
