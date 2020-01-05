package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.*;

import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadOptionAction extends NativeAction
{
  private static final String OUTPUT_CHOSEN_OPTION = "ChosenOption";
  private static final String PARAMETER_NAME_OPTIONS = "OptionsCSV";

  @Override
  public InvocationResults invoke(GameState gameState, CallDetails callDetails)
  {
    String optionsText = gameState.getVariables().lookupVariableValue(PARAMETER_NAME_OPTIONS);
    String[] options = optionsText.split(",");
    System.out.println("<<< Choose option: " + Arrays.toString(options));
    HashMap<String, String> results = new HashMap<>();
    results.put(OUTPUT_CHOSEN_OPTION, "${buffer}");
    return new InvocationResults(new Variables("return", results), ReturnMode.SUSPEND);
  }
}
