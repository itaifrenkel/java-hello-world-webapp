package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.CallDetails;
import com.github.dagwud.woodlands.game.commands.invocation.InvocationResults;
import com.github.dagwud.woodlands.game.commands.invocation.ReturnMode;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadTextInputAction extends NativeAction
{
  private static final String OUTPUT_CAPTURED_TEXT = "CapturedText";

  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails)
  {
    Variables results = new Variables();
    results.put(OUTPUT_CAPTURED_TEXT, "${buffer}");
    return new InvocationResults(results, ReturnMode.SUSPEND);
  }
}
