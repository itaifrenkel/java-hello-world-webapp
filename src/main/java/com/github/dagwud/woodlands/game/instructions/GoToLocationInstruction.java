package com.github.dagwud.woodlands.game.instructions;

import com.github.dagwud.woodlands.game.commands.invocation.Variables;

public class GoToLocationInstruction extends RunProcInstruction
{
  private final String locationName;

  GoToLocationInstruction(String locationName)
  {
    super("Goto", buildVariables(locationName), buildVariables(locationName));
    this.locationName = locationName;
  }

  private static Variables buildVariables(String locationName)
  {
    Variables inputs = new Variables();
    inputs.put("NewLocation", locationName); //todo only one of these (inputs or params) should be necessary
    return inputs;
  }
}
