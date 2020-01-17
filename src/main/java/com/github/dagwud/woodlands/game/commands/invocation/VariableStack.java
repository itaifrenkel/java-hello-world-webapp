package com.github.dagwud.woodlands.game.commands.invocation;

import java.util.Stack;

public class VariableStack
{
  private final Stack<Variables> stack;

  public VariableStack()
  {
    stack = new Stack<>();
    pushNewVariablesStackFrame("<root>", new Variables());
  }

  void pushNewVariablesStackFrame(String stackName, Variables callParameters)
  {
    stack.push(new Variables(stackName, callParameters));
  }

  public void dropStackFrame()
  {
    stack.pop();
  }

  public String pretty()
  {
    StringBuilder b = new StringBuilder();
    b.append("===================\n");
    for (int i = 0; i < stack.size(); i++)
    {
      String s = stack.get(i).pretty(i);
      b.append(s).append("\n");
    }
    b.append("===================");
    return b.toString();
  }

}
