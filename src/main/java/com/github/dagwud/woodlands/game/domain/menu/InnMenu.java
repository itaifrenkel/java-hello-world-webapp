package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class InnMenu extends GameMenu
{
  public InnMenu()
  {
    setPrompt("This is the inn");
    setOptions(ECommand.RETRIEVE_ITEMS, ECommand.LEAVE_ITEMS, ECommand.SHORT_REST, ECommand.SWITCH_CHARACTERS, ECommand.CHANGE_NAME, ECommand.VILLAGE_SQUARE);
  }
}
