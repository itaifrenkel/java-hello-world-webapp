package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class DeepWoodsMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public DeepWoodsMenu()
  {
    setPrompt("This is the Deep Woods");
    setOptions(ECommand.THE_WOODLANDS, ECommand.CAST_A_SPELL);
  }
}
