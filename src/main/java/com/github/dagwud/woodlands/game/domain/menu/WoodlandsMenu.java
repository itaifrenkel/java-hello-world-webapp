package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;

public class WoodlandsMenu extends GameMenu
{
    public WoodlandsMenu()
    {
        setPrompt("This is the woodlands");
        setOptions(ECommand.THE_VILLAGE);
    }
}
