package com.github.dagwud.woodlands.game.commands.conditional;

public class CompareEqualEvaluator extends ConditionEvaluator
{
  CompareEqualEvaluator(String condition)
  {
    super(condition);
  }

  @Override
  public boolean evaluatesToTrue()
  {
    int split = expression.indexOf("==");
    String left = expression.substring(0, split);
    String right = expression.substring(split + "==".length());
    return left.equals(right);
  }
}
