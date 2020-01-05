package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.*;

import java.io.IOException;

public abstract class NativeAction
{
  public abstract InvocationResults invoke(GameState gameState, CallDetails callDetails) throws ActionInvocationException, IOException;
}
