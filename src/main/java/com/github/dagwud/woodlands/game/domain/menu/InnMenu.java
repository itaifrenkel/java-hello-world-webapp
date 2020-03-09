package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.ECommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InnMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public InnMenu()
  {
    setPrompt("<i>This is the inn</i>");
    setOptions(ECommand.SHORT_REST, ECommand.LONG_REST,
            ECommand.RETRIEVE_ITEMS, ECommand.LEAVE_ITEMS,
            ECommand.SWITCH_CHARACTERS, ECommand.CHANGE_NAME,
            ECommand.VILLAGE_SQUARE);
  }

  @Override
  public String[] produceOptions(PlayerState playerState)
  {
    boolean innKeeperIsCarrying = playerState.getActiveCharacter().getInnkeeper().getCarrying().countTotalCarried() > 0;

    if (innKeeperIsCarrying)
    {
      return super.produceOptions(playerState);
    }

    List<String> options = new ArrayList<>(Arrays.asList(super.produceOptions(playerState)));
    options.remove(ECommand.RETRIEVE_ITEMS.getMenuText());
    return options.toArray(new String[0]);
  }
}
