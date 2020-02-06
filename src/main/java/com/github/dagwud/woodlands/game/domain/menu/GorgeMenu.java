package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class GorgeMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public GorgeMenu()
  {
    setPrompt("This is the Gorge. Here be dragons!");
    setOptions(ECommand.DEEP_WOODS, ECommand.CAST_A_SPELL);
  }
}
