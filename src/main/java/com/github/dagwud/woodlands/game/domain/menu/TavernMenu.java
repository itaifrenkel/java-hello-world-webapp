package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

public class TavernMenu extends GameMenu
{
  public TavernMenu()
  {
    String message = "The smell of spilled beer permeates the local tavern, and the floor crunches slightly underfoot as you walk across the dirt from dozens of well-worn shoes.\n" +
    "\n" +
    "The Raven is a friendly place, where all are welcome as long as they pay for their drinks. A burly guard with a dark oversized coat stands watch, and the magic-imbued rings on a chain around his neck clearly signal this is not a man to be trifled with. \n" +
    "Apart from the guard, who looks permanently annoyed, the patrons of The Raven are all smiles - with the largest being the one plastered over the face of the barman.\n" +
    "\n" +
    “Long journey?” he asks jovially, though something about his demeanor suggests he’s not that interested in your journey so much as how many coins are in your pocket");

    setPrompt("This is the tavern");
    setOptions(new String[]{"Buy Drinks", "Village Square"});
  }
}
