package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class TavernMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public TavernMenu()
  {
    setPrompt("<i>This is the Tavern</i>");
    setOptions(ECommand.BUY_DRINKS, ECommand.VILLAGE_SQUARE);
  }
}
