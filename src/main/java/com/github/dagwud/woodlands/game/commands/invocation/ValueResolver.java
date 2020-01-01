package com.github.dagwud.woodlands.game.commands.invocation;

abstract class ValueResolver
{

  public static final String START_VARIABLE = "${";
  public static final String END_VARIABLE = "}";

  private ValueResolver()
  {
  }

  static String resolve(String expression, ActionParameters callParameters) throws VariableUndefinedException
  {
    if (!expression.contains(START_VARIABLE))
    {
      return expression;
    }

    ExpressionTree toks = new ExpressionTree(expression, START_VARIABLE, END_VARIABLE);
    replaceVars(toks.getRoot(), callParameters);

    String resolved = toks.collapse();
    System.out.println("resolve " + expression + " --> " + resolved);
    return resolved;
  }

  private static void replaceVars(ExpressionTreeNode expressionTreeNode, ActionParameters callParameters)
  {
    if (expressionTreeNode.getValue().contains(START_VARIABLE))
    {
      String value = resolveVar(expressionTreeNode.getValue(), callParameters);
      expressionTreeNode.setValue(value);
    }
    if (null != expressionTreeNode.getRight())
    {
      replaceVars(expressionTreeNode.getRight(), callParameters);
    }
  }

  private static String resolveVar(String varExpression, ActionParameters callParameters) throws VariableUndefinedException
  {
    String varName = varExpression.substring(START_VARIABLE.length(), varExpression.length() - END_VARIABLE.length());
    String value = callParameters.get(varName);
    if (value == null)
    {
      throw new VariableUndefinedException(varExpression);
    }
    return value;
  }
}
