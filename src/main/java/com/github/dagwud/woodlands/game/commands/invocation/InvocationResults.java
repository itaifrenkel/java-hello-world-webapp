package com.github.dagwud.woodlands.game.commands.invocation;

public class InvocationResults
{
  private final Variables variables;
  private final ReturnMode returnMode;

  public InvocationResults(Variables variables)
  {
    this(variables, ReturnMode.CONTINUE);
  }

  public InvocationResults(Variables variables, ReturnMode returnMode)
  {
    this.variables = variables;
    this.returnMode = returnMode;
  }

  public Variables getVariables()
  {
    return variables;
  }

  ReturnMode getReturnMode()
  {
    return returnMode;
  }
}
