package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionResults;

public class ReadTextInputAction extends NativeAction
{
  @Override
  public ActionResults invoke()
  {
    System.out.println(">>> ");
    return new ActionResults();
  }
}
