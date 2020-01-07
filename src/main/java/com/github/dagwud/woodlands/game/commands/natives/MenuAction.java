package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.ReturnMode;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

@SuppressWarnings("unused") // called at runtime via reflection
public class MenuAction extends ReadOptionAction
{
  @Override
  protected InvocationResults buildResults(Variables results)
  {
    return new InvocationResults(results, ReturnMode.CONTINUE);
  }
}
