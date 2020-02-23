package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class ManualEncounterMenu extends ActiveSpellsMenu
{
  private static final long serialVersionUID = 1L;

  public ManualEncounterMenu(String planningDuration)
  {
    setPrompt("What will you do? Battle commences in " + planningDuration);
    setOptions(ECommand.VILLAGE_SQUARE, ECommand.ATTACK);
  }
}
