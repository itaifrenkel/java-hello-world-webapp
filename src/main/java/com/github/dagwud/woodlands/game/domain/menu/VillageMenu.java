package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.commands.ECommand;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class VillageMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public VillageMenu()
  {
    setPrompt("<i>You are at The Village</i>");
    setOptions(ECommand.JOIN, ECommand.CLAIM_ITEM,
        ECommand.THE_INN, ECommand.THE_TAVERN,
        ECommand.BLACKSMITH,
        ECommand.THE_PETTING_ZOO, ECommand.THE_MOUNTAIN,
        ECommand.THE_WOODLANDS);
  }

  @Override
  public String produceEntryText(PlayerCharacter playerState, ELocation from)
  {
    if (from == ELocation.TAVERN)
    {
      return fmt(playerState, "%s crawls into the square from the Tavern.");
    }
    else if (from == ELocation.INN)
    {
      return fmt(playerState, "%s arrives from the Inn, looking rested and bed-bug-bite-free.");
    }
    else if (from == ELocation.MOUNTAIN)
    {
      return fmt(playerState, "The sounds of distant battle clash out, and %s stumbles in from the Mountain, covered in blood and small furry bits.");
    }
    else if (from == ELocation.PETTING_ZOO)
    {
      return fmt(playerState, "%s walks in from the Petting Zoo, looking sweaty and slightly embarrassed.");
    }
    else if (from == ELocation.WOODLANDS)
    {
      return fmt(playerState, "%s is thrown into the Village Square, the sound of Woodlands beasties coming from behind.");
    }

    return null;
  }

  @Override
  public String produceExitText(PlayerCharacter playerState, ELocation to)
  {
    if (to == ELocation.TAVERN)
    {
      return fmt(playerState, "%s saunters into the Tavern, attempting to look casual.");
    }
    else if (to == ELocation.INN)
    {
      return fmt(playerState, "With a yawn, %s slumps their way towards the Inn.");
    }
    else if (to == ELocation.MOUNTAIN)
    {
      return fmt(playerState, "%s strides determinedly towards the Mountain - adventure awaits.");
    }
    else if (to == ELocation.WOODLANDS)
    {
      return fmt(playerState, "%s heads off to abuse innocent Woodlands creatures.");
    }
    else if (to == ELocation.PETTING_ZOO)
    {
      return fmt(playerState, "%s walks towards the charming children's Petting Zoo, with a weapon for some reason.");
    }

    return null;
  }

  private String fmt(PlayerCharacter playerState, String pattern)
  {
    return String.format(pattern, playerState.getName());
  }
}
