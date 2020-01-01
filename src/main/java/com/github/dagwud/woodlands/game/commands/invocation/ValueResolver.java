package com.github.dagwud.woodlands.game.commands.invocation;

abstract class ValueResolver
{
  private ValueResolver()
  {
  }

  static String resolve(String expression, ActionParameters callParameters) throws VariableUndefinedException
  {
    if (!expression.contains("${"))
    {
      return expression;
    }

    ExpressionTree toks = new ExpressionTree(expression, "${", "}");
    replaceVars(toks.root, callParameters);

    String resolved = toks.collapse();
    System.out.println("resolve " + expression + " --> " + resolved);
    return resolved;
  }

  private static void replaceVars(ExpressionTreeNode expressionTreeNode, ActionParameters callParameters)
  {
    if (expressionTreeNode.getValue().contains("${"))
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
    String varName = varExpression.substring("${".length(), varExpression.length() - "}".length());
    String value = callParameters.get(varName);
    if (value == null)
    {
      throw new VariableUndefinedException(varExpression);
    }
    return value;
  }
}
