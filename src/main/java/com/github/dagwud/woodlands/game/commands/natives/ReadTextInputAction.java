package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionParameters;
import com.github.dagwud.woodlands.game.commands.invocation.ActionResults;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadTextInputAction extends NativeAction
{
  @Override
  public ActionResults invoke(ActionParameters callParameters)
  {
    // todo requireNoParameters(callParameters);
    System.out.println("READ TEXT");
    return new ActionResults();
  }
}
