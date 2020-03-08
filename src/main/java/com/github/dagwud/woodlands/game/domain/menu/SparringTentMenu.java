package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class SparringTentMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public SparringTentMenu()
  {
    setPrompt("<i>This is the Sparring Tent</i>");
    setOptions(ECommand.THE_TAVERN);
  }

  @Override
  public String produceEntryText(PlayerCharacter playerState, ELocation from)
  {
    return playerState.getName() + " strides into the tent, looking for a fight";
  }

  @Override
  public String produceExitText(PlayerCharacter playerState, ELocation to)
  {
    return playerState.getName() + " hurries back into the bar";
  }
}
