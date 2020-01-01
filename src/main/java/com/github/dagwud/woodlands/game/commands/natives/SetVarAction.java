package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionCallContext;
import com.github.dagwud.woodlands.game.commands.invocation.ActionResults;

@SuppressWarnings("unused") // called at runtime via reflection
public class SetVarAction extends NativeAction
{
  @Override
  public ActionResults invoke(ActionCallContext parameters)
  {
    System.out.println("SET VAR");
    return new ActionResults();
  }
}
