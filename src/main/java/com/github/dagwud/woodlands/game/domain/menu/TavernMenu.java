package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class TavernMenu extends GameMenu
{
  public TavernMenu()
  {
    setPrompt("This is the Tavern");
    setOptions(ECommand.BUY_DRINKS, ECommand.VILLAGE_SQUARE);
  }
}
