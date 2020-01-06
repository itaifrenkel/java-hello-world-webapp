package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.*;

import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadTextInputAction extends NativeAction
{
  private static final String OUTPUT_CAPTURED_TEXT = "CapturedText";

  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails)
  {
    HashMap<String, String> results = new HashMap<>();
    results.put(OUTPUT_CAPTURED_TEXT, "${buffer}");
    return new InvocationResults(new Variables(results), ReturnMode.SUSPEND);
  }
}
