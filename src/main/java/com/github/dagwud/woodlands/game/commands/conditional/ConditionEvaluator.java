package com.github.dagwud.woodlands.game.commands.conditional;

public abstract class ConditionEvaluator
{
  final String expression;

  ConditionEvaluator(String expression)
  {
    this.expression = expression;
  }

  public abstract boolean evaluatesToTrue();
}
