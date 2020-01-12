package com.github.dagwud.woodlands.game.commands.conditional;

import com.github.dagwud.woodlands.game.commands.invocation.ValueResolver;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;
import com.github.dagwud.woodlands.game.commands.values.WoodlandsRuntimeException;

public abstract class ConditionEvaluatorFactory
{
  private ConditionEvaluatorFactory()
  {
  }

  public static ConditionEvaluator createEvaluator(String condition, VariableStack callParameters)
  {
    condition = ValueResolver.resolve(condition, callParameters);
    if (condition.contains("=="))
    {
      return new CompareEqualEvaluator(condition);
    }
    if (condition.contains("!="))
    {
      return new CompareNotEqualEvaluator(condition);
    }
    throw new WoodlandsRuntimeException("Unable to evaluate expression: " + condition);
  }
}
