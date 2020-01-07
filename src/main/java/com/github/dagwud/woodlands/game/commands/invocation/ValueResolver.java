package com.github.dagwud.woodlands.game.commands.invocation;

public abstract class ValueResolver
{

  public static final String START_VARIABLE = "${";
  static final String END_VARIABLE = "}";

  private ValueResolver()
  {
  }

  static String resolve(String expression, VariableStack callParameters) throws VariableUndefinedException
  {
    if (!expression.contains(START_VARIABLE))
    {
      return expression;
    }

    ExpressionTree toks = new ExpressionTree(expression, START_VARIABLE, END_VARIABLE);
    resolveVars(toks.getRoot(), callParameters);

    //    System.out.println("resolved " + expression + " --> " + resolved);
    return toks.collapse();
  }

  private static void resolveVars(ExpressionTreeNode expressionTreeNode, VariableStack callParameters)
  {
    if (expressionTreeNode.getValue().contains(START_VARIABLE))
    {
      String value = resolveVar(expressionTreeNode.getValue(), callParameters);
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
}
