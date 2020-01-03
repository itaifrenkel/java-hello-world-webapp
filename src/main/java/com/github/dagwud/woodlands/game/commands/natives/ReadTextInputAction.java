package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.ActionParameters;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadTextInputAction extends NativeAction
{
  private static final String OUTPUT_CAPTURED_TEXT = "CapturedText";

  @Override
  public ActionParameters invoke(Variables context)
  {
    System.out.println("<<< READ TEXT");
    HashMap<String, String> results = new HashMap<>();
    results.put(OUTPUT_CAPTURED_TEXT, "DummyValue");
    return new ActionParameters("return", results);
  }
}
