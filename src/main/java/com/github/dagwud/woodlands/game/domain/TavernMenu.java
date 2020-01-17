package com.github.dagwud.woodlands.game.domain;

class TavernMenu extends GameMenu
{
  TavernMenu()
  {
    setPrompt("This is the tavern");
    setOptions(new String[]{"Buy Drinks", "Village Square"});
  }
}
