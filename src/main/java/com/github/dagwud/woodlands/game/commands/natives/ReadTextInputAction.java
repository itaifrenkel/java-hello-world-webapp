package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionParameters;
import com.github.dagwud.woodlands.game.commands.invocation.ActionResults;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadTextInputAction extends NativeAction
{
  private static final String OUTPUT_CAPTURED_TEXT = "CapturedText";

  @Override
  public ActionResults invoke(ActionParameters parameters)
  {
    System.out.println("<<< READ TEXT");
    ActionResults actionResults = new ActionResults();
    actionResults.put(OUTPUT_CAPTURED_TEXT, "DummyValue");
    return actionResults;
  }
}
