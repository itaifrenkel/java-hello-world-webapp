package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadTextInputAction extends NativeAction
{
  private static final String OUTPUT_CAPTURED_TEXT = "CapturedText";

  @Override
  public Variables invoke(VariableStack context)
  {
    System.out.println("<<< READ TEXT");
    HashMap<String, String> results = new HashMap<>();
    results.put(OUTPUT_CAPTURED_TEXT, "DummyValue");
    return new Variables("return", results);
  }
}
