package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class InnMenu extends GameMenu
{
  public InnMenu()
  {
    setPrompt("This is the inn");
    setOptions(ECommand.SHORT_REST, ECommand.VILLAGE_SQUARE, ECommand.RETRIEVE_ITEMS, ECommand.LEAVE_ITEMS, ECommand.SWITCH_CHARACTERS, ECommand.CHANGE_NAME);
  }
}
