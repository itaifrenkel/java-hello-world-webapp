package com.github.dagwud.woodlands.game.commands.invocation;

public class ActionCallContext
{
  private final Variables callParameters;

  ActionCallContext()
  {
    this.callParameters = new Variables();
  }

  public Variables getCallParameters()
  {
    return callParameters;
  }
}
