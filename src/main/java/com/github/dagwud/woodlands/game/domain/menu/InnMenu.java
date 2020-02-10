package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class InnMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public InnMenu()
  {
    setPrompt("<i>This is the inn</i>");
    setOptions(ECommand.SHORT_REST, ECommand.VILLAGE_SQUARE, ECommand.RETRIEVE_ITEMS, ECommand.LEAVE_ITEMS, ECommand.SWITCH_CHARACTERS, ECommand.CHANGE_NAME);
  }
}
