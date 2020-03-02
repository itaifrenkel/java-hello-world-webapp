package com.github.dagwud.woodlands.game.domain.menu.cavern;

import com.github.dagwud.woodlands.game.commands.Command;
import com.github.dagwud.woodlands.game.commands.ECommand;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.menu.GameMenu;

public class CavernEntranceMenu extends GameMenu
{
  public CavernEntranceMenu()
  {
    setPrompt("You are at the entrance to the caverns");
    setOptions(ECommand.DEEP_WOODS, new Command("Into the Cavern", true,
            ((character, chatId) -> new MoveToLocationCmd(character, ELocation.CAVERN_1))));
  }
}
