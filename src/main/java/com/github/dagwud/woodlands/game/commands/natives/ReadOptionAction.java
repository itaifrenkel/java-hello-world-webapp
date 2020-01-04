package com.github.dagwud.woodlands.game.commands.natives;

import com.github.dagwud.woodlands.game.GameState;
import com.github.dagwud.woodlands.game.commands.invocation.VariableStack;
import com.github.dagwud.woodlands.game.commands.invocation.Variables;

import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("unused") // called at runtime via reflection
public class ReadOptionAction extends NativeAction
{
  private static final String OUTPUT_CHOSEN_OPTION = "ChosenOption";
  private static final String PARAMETER_NAME_OPTIONS = "OptionsCSV";

  @Override
  public Variables invoke(GameState gameState, VariableStack context)
  {
    String optionsText = context.lookupVariableValue(PARAMETER_NAME_OPTIONS);
    String[] options = optionsText.split(",");
    System.out.println("<<< Choose option: " + Arrays.toString(options));
    HashMap<String, String> results = new HashMap<>();
    results.put(OUTPUT_CHOSEN_OPTION, options[options.length - 1]);
    return new Variables("return", results);
  }
}
