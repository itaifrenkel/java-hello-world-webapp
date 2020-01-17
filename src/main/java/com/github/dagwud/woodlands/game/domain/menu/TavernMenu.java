package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

public class TavernMenu extends GameMenu
{
  public TavernMenu()
  {
    setPrompt("This is the tavern");
    setOptions(new String[]{"Buy Drinks", "Village Square"});
  }
}
