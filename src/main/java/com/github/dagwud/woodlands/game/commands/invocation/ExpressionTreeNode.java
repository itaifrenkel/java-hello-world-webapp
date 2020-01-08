package com.github.dagwud.woodlands.game.commands.invocation;

import com.github.dagwud.woodlands.game.commands.values.ValueParseException;

class ExpressionTreeNode
{
  private String value;
  private ExpressionTreeNode right;

  ExpressionTreeNode(String value)
  {
    this.value = value;
    this.right = null;
  }

  void split(String start, String end)
  {
    if (value.contains(start))
    {
      int startIndex = value.indexOf(start);
      int endIndex = findClosing(value, start, end, startIndex);
      if (endIndex == -1)
      {
        throw new ValueParseException("No matching '" + end + "' for '" + start + "' in expression '" + value + "'");
      }
      endIndex += end.length();

      if (startIndex == 0 && endIndex == value.length())
      {
        return;
      }
      int splitPoint = startIndex != 0 ? startIndex : endIndex;
      split(start, end, splitPoint);
    }
  }

  private void split(String start, String end, int splitPoint)
  {
    String newValue = value.substring(0, splitPoint);
    String newRight = value.substring(splitPoint);
    this.value = newValue;
    this.right = new ExpressionTreeNode(newRight);
    right.split(start, end);
  }

  private int findClosing(String str, String starting, String closing, int startWith)
  {
    int indent = 0;
    for (int i = startWith; i + closing.length() <= str.length(); i++)
    {
      if (i + starting.length() <= str.length() && str.substring(i, i + starting.length()).equals(starting))
      {
        indent++;
      }
      else if (str.substring(i, i + closing.length()).equals(closing))
      {
        indent--;
        if (indent == 0)
        {
          return i;
        }
      }
    }
    return -1;
  }

  String getValue()
  {
    return value;
  }

  void setValue(String value)
  {
    this.value = value;
  }

  ExpressionTreeNode getRight()
  {
    return right;
  }

  void collapse(StringBuffer b)
  {
    b.append(value);
    if (null != right)
    {
      right.collapse(b);
    }
  }
}
