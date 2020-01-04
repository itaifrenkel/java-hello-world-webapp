package com.github.dagwud.woodlands.game;

import com.github.dagwud.woodlands.game.commands.invocation.ActionInvoker;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;

public class SuspendedInvocation
{
  private final ActionInvoker actionInvoker;
  private final GameState gameState;
  private final CallDetails callDetails;

  public SuspendedInvocation(ActionInvoker actionInvoker, GameState gameState, CallDetails callDetails)
  {
    this.actionInvoker = actionInvoker;
    this.gameState = gameState;
    this.callDetails = callDetails;
  }
}
