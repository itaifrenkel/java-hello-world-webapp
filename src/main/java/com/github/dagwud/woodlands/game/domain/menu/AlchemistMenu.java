package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class AlchemistMenu extends GameMenu
{
  public AlchemistMenu()
  {
    setPrompt("<i>This is the Alchemist's store</i>");
    setOptions(ECommand.VILLAGE_SQUARE, ECommand.ENCHANT_ITEM);
  }
}
