package com.github.dagwud.woodlands.game.domain;

class InnMenu extends GameMenu
{
  InnMenu()
  {
    setPrompt("This is the inn");
    setOptions(new String[]{"Leave Items", "Retrieve Items", "Short Rest", "Village Square"});
  }
}
