package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class MountainMenu extends ActiveSpellsMenu
{
  private static final long serialVersionUID = 1L;

  public MountainMenu()
  {
    setPrompt("<i>This is the Mountain</i>");
    setOptions(ECommand.VILLAGE_SQUARE);
  }
}
