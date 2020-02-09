package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class VillageMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public VillageMenu()
  {
    setPrompt("<i>You are at The Village</i>");
    setOptions(ECommand.JOIN, ECommand.THE_INN, ECommand.THE_TAVERN, ECommand.THE_MOUNTAIN, ECommand.THE_WOODLANDS);
  }
}
