package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class AlchemistMenu extends GameMenu
{
  public AlchemistMenu()
  {
    setPrompt("<i>This is the Alchemist's store</i>");
    setOptions(ECommand.ENCHANT_ITEM, ECommand.ENCHANT_SHIELD, ECommand.VILLAGE_SQUARE);
  }
}
