package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.ECommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TavernMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public TavernMenu()
  {
    setPrompt("<i>This is the Tavern</i>");
    setOptions(ECommand.BUY_DRINKS, ECommand.WAKE);
  }

  @Override
  public String[] produceOptions(PlayerState playerState)
  {
    // Arrays.asList produces an immutable list
    List<String> options = new ArrayList<>(Arrays.asList(super.produceOptions(playerState)));
    if (playerState.getActiveCharacter().getStats().getAvailableStatsPointUpgrades() > 0)
    {
      options.add(ECommand.UPGRADE.toString());
    }

    options.add(ECommand.VILLAGE_SQUARE.toString());

    return options.toArray(new String[0]);
  }
}
