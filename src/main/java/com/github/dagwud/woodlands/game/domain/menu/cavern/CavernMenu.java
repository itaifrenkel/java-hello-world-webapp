package com.github.dagwud.woodlands.game.domain.menu.cavern;

import com.github.dagwud.woodlands.game.commands.ECommand;
import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

public abstract class CavernMenu extends GameMenu
{
  public CavernMenu(ECommand... options)
  {
    setPrompt("You are in a cavern");
    options = buildOptions(options);
    setOptions(options);
  }

  protected ECommand[] buildOptions(ECommand[] options)
  {
    return options; // todo shuffle them
  }

}
