package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

public class WoodlandsMenu extends GameMenu
{
  public WoodlandsMenu()
  {
    setPrompt("This is the woodlands");
    setOptions(new String[]{"The Gorge", "The Village"});
  }
}
