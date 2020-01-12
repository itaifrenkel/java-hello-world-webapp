package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.natives.MathEvaluator;

public abstract class ValueResolver
{

  static final String START_VARIABLE = "${";
  static final String END_VARIABLE = "}";

  private ValueResolver()
  {
  }

  public static String resolve(String expression, VariableStack callParameters) throws VariableUndefinedException
  {
    if (!expression.contains(START_VARIABLE))
    {
      return eval(expression);
    }

    ExpressionTree toks = new ExpressionTree(expression, START_VARIABLE, END_VARIABLE);
    resolveVars(toks.getRoot(), callParameters);

    //    System.out.println("resolved " + expression + " --> " + resolved);
    return eval(toks.collapse());
  }

  private static void resolveVars(ExpressionTreeNode expressionTreeNode, VariableStack callParameters)
  {
    if (expressionTreeNode.getValue().contains(START_VARIABLE))
    {
      String value;
      value = resolveVar(expressionTreeNode.getValue(), callParameters);
      expressionTreeNode.setValue(value);
    }
    if (null != expressionTreeNode.getRight())
    {
      resolveVars(expressionTreeNode.getRight(), callParameters);
    }
  }

  private static String resolveVar(String varExpression, VariableStack callParameters) throws VariableUndefinedException
  {
    String varName = varExpression.substring(START_VARIABLE.length(), varExpression.length() - END_VARIABLE.length());
    if (varName.contains(START_VARIABLE))
    {
      // Compound variables - i.e. where the outer variable name is built using
      // another variable name. For example, ${some${abc}} where abc="thing", result is the value
      // of the variable ${something}:
      varName = resolve(varName, callParameters);
    }

    String value = callParameters.lookupVariable(varName);
    if (value.contains(START_VARIABLE))
    {
      if (value.contains(varExpression))
      {
        throw new RuntimeException("Attempt made to create a self-referential variable: " + varExpression + " = " + value);
      }
      value = ValueResolver.resolve(value, callParameters);
    }
    return value;
  }

  private static String eval(String expression)
  {
    if (expression.contains("eval("))
    {
      return evaluateMath(expression);
    }
    return expression;
  }

  private static String evaluateMath(String expr)
  {
    expr = expr.substring("eval(".length());
    expr = expr.substring(0, expr.length() - ")".length());
    double result = MathEvaluator.eval(expr);

    String resultString = String.format("%s", result);
    if (result == (long) result)
    {
      resultString = String.format("%d", (long) result);
    }
    System.out.println("EVAL: " + expr + " = " + resultString);
    return resultString;
  }

}
