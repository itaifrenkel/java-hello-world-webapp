package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionCallContext;
import com.github.dagwud.woodlands.game.commands.invocation.ActionResults;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadTextInputAction extends NativeAction
{
  private static final String OUTPUT_CAPTURED_TEXT = "CapturedText";

  @Override
  public ActionResults invoke(ActionCallContext context)
  {
    System.out.println("<<< READ TEXT");
    ActionResults actionResults = new ActionResults();
    actionResults.put(OUTPUT_CAPTURED_TEXT, "DummyValue");
    return actionResults;
  }
}
