package com.github.dagwud.woodlands.game.domain;

class WoodlandsMenu extends GameMenu
{
  WoodlandsMenu()
  {
    setPrompt("This is the woodlands");
    setOptions(new String[]{"The Gorge", "The Village"});
  }
}
