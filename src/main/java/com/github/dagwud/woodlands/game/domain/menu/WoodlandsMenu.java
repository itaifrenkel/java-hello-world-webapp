package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class WoodlandsMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public WoodlandsMenu()
  {
    setPrompt("This is the woodlands");
    setOptions(ECommand.VILLAGE_SQUARE, ECommand.CAST_A_SPELL);
  }
}
