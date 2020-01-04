package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.ActionInvocationException;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;

import java.io.IOException;

public abstract class NativeAction
{
  public void verifyParameters(VariableStack parameters) throws ActionParameterException
  {
  }

  public abstract Variables invoke(GameState gameState) throws ActionInvocationException, IOException;
}
