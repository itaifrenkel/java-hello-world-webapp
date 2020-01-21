package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class MountainMenu extends GameMenu
{
  public MountainMenu()
  {
    setPrompt("This is the Mountain");
    setOptions(ECommand.VILLAGE_SQUARE);
  }
}
