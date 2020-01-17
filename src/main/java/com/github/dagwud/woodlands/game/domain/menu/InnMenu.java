package com.github.dagwud.woodlands.game.domain.menu;

public class InnMenu extends GameMenu
{
  public InnMenu()
  {
    setPrompt("This is the inn");
    setOptions(new String[]{"Leave Items", "Retrieve Items", "Short Rest", "Village Square"});
  }
}
