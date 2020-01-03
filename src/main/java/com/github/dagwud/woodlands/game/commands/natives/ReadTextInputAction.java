package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionCallContext;
import com.github.dagwud.woodlands.game.commands.invocation.ActionParameters;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadTextInputAction extends NativeAction
{
  private static final String OUTPUT_CAPTURED_TEXT = "CapturedText";

  @Override
  public ActionParameters invoke(ActionCallContext context)
  {
    System.out.println("<<< READ TEXT");
    HashMap<String, String> results = new HashMap<>();
    results.put(OUTPUT_CAPTURED_TEXT, "DummyValue");
    return new ActionParameters("return", results);
  }
}
