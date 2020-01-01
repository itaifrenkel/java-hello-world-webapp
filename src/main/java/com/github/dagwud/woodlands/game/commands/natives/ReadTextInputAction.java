package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionResults;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadTextInputAction extends NativeAction
{
  @Override
  public ActionResults invoke()
  {
    System.out.println(">>> ");
    return new ActionResults();
  }
}
