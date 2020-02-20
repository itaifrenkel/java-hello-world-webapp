package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class PettingZooMenu extends ActiveSpellsMenu
{
  private static final long serialVersionUID = 1L;

  public PettingZooMenu()
  {
    setPrompt("<i>This is the Petting Zoo</i>");
    setOptions(ECommand.VILLAGE_SQUARE);
  }
}
