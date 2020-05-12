package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.ECommand;
import com.github.dagwud.woodlands.game.commands.poker.HardFoldCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.npc.PokerDealer;
import za.co.knonchalant.pokewhat.domain.Game;
import za.co.knonchalant.pokewhat.domain.lookup.EGameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackRoomMenu extends GameMenu
{
  private static final long serialVersionUID = 1L;

  public BackRoomMenu()
  {
    setPrompt("<i>This is the Back Room of the Tavern</i>");
    setOptions(ECommand.THE_TAVERN);
  }

  @Override
  public String produceEntryText(PlayerCharacter playerState, ELocation from)
  {
    return playerState.getName() + " walks into the back room, looking around nervously";
  }

  @Override
  public String produceExitText(PlayerCharacter playerState, ELocation to)
  {
    Party party = playerState.getParty();
    if (!party.isPrivateParty())
    {
      PokerDealer pokerDealer = party.getPokerDealer();
      if (pokerDealer.playerIsInGame(playerState))
      {
        CommandDelegate.execute(new HardFoldCmd(playerState));
        return playerState.getName() + " throws down their cards and runs out of the room.";
      }
    }

    return playerState.getName() + " hurries back into the bar"; // add indication if they won or lost money
  }

  @Override
  public String[] produceOptions(PlayerState playerState)
  {
    List<String> options = new ArrayList<>(Arrays.asList(super.produceOptions(playerState)));

    PlayerCharacter character = playerState.getActiveCharacter();
    PokerDealer pokerDealer = character.getParty().getPokerDealer();

    if (!pokerDealer.playerIsInGame(character))
    {
      options.add(ECommand.SIT_AT_POKER.getMenuText());
    }
    else
    {
      if (pokerDealer.currentPlayerIs(character))
      {
        options.add(ECommand.BET.getMenuText());
        double currentBet = pokerDealer.getCurrentBetFor(character);
        double amountNeeded = pokerDealer.getCurrentGame().getCurrentBet() - currentBet;
        if (amountNeeded <= 0)
        {
          options.add(ECommand.CHECK.getMenuText());
        }
        else
        {
          options.add(ECommand.FOLD.getMenuText());
        }
      }

      if (pokerDealer.getCurrentGame().getState() == EGameState.NOT_STARTED)
      {
        options.add(ECommand.START_GAME.getMenuText());
      }
    }

    return options.toArray(new String[0]);
  }
}
