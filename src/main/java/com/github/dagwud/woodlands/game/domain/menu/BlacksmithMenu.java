package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class BlacksmithMenu extends GameMenu
{
  public BlacksmithMenu()
  {
    setPrompt("<i>This is the Blacksmith's shop</i>");
    setOptions(ECommand.VILLAGE_SQUARE, ECommand.CRAFT_WEAPON);
  }
}
