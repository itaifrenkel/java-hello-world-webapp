package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

public class VillageMenu extends GameMenu
{
  public VillageMenu()
  {
    setPrompt("The bustle of the village square brings a reassuring comfort to weary adventurers - the only danger here is the prices charged by the local bartender.\n\nThe square is dominated on the one side by the local tavern, from which you hear loud music and occasional raised voices.\n\nFacing it, within stumbling range, is the Inn. The innkeeper is a friend, and has offered to look after any items you may wish to leave behind while you're out on your adventures.\n\nTo the north lies a path leading up to The Mountain, and another leading out to the Woodlands.");
    setOptions(new String[]{"The Inn", "The Tavern", "The Mountain", "The Woodlands"});
  }
}
