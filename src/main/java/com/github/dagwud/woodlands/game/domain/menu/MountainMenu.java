package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class MountainMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public MountainMenu()
  {
    setPrompt("This is the Mountain");
    setOptions(ECommand.VILLAGE_SQUARE, ECommand.CAST_A_SPELL);
  }
}
