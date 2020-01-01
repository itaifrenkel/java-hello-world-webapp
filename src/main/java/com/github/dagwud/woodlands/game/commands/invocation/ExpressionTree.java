package com.github.dagwud.woodlands.game.commands.invocation;

class ExpressionTree
{
  private ExpressionTreeNode root;

  ExpressionTree(String expression, String start, String end)
  {
    root = new ExpressionTreeNode(expression);
    root.split(start, end);
  }

  String collapse()
  {
    StringBuffer b = new StringBuffer();
    root.collapse(b);
    return b.toString();
  }

  ExpressionTreeNode getRoot()
  {
    return root;
  }
}
