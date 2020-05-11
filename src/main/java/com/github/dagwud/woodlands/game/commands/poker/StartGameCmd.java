package com.github.dagwud.woodlands.game.commands.poker;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendLocationMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.npc.PokerDealer;
import za.co.knonchalant.pokewhat.domain.Game;
import za.co.knonchalant.pokewhat.domain.Player;
import za.co.knonchalant.pokewhat.domain.lookup.EGameState;

import java.util.Map;

public class StartGameCmd extends AbstractCmd
{
  private final PlayerCharacter character;

  public StartGameCmd(PlayerCharacter character)
  {
    super(new NotInPrivatePartyPrerequisite(character));
    this.character = character;
  }

  @Override
  public void execute()
  {
    Party party = character.getParty();

    PokerDealer pokerDealer = party.getPokerDealer();
    if (!pokerDealer.playerIsInGame(character))
    {
      // shouldn't happen anyways
      tellPlayer("You can't start the game until you're in the game.");
      return;
    }

    Game currentGame = pokerDealer.getCurrentGame();

    if (currentGame.getState() == EGameState.WAITING_FOR_PLAYERS)
    {
      tellPlayer("You'd be playing with yourself - you need someone to play against.");
      return;
    }

    if (currentGame.getState() == EGameState.NOT_STARTED)
    {
      currentGame.start();
      Map<Player, Double> bets = currentGame.getBets();
      double blind = currentGame.getBlind();

      Player bigBlind = null;
      Player littleBlind = null;
      for (Map.Entry<Player, Double> playerDoubleEntry : bets.entrySet())
      {
        if (playerDoubleEntry.getValue() == blind)
        {
          littleBlind = playerDoubleEntry.getKey();
        }
        else if (playerDoubleEntry.getValue() == 2 * blind)
        {
          bigBlind = playerDoubleEntry.getKey();
        }
      }

      if (bigBlind == null || littleBlind == null)
      {
        return;
      }

      tellPlayer("If not, why not? You propose starting the game and it begins. " +
              bigBlind.getName() + " is big blind with a bet of " + 2 * blind +", " + littleBlind.getName() + " is little blind with a bet of " + blind +". Next to bet is " + currentGame.getCurrentPlayer().getName());

      tellRoom("Game has started! " +
              bigBlind.getName() + " is big blind with a bet of " + 2 * blind +", " + littleBlind.getName() + " is little blind with a bet of " + blind +". Next to bet is " + currentGame.getCurrentPlayer().getName());

      for (PlayerCharacter player : pokerDealer.getPlayers())
      {
        CommandDelegate.execute(new ShowMenuCmd(player.getLocation().getMenu(), player.getPlayedBy().getPlayerState()));
      }
    }
  }

  private void tellPlayer(String s)
  {
    CommandDelegate.execute(new SendMessageCmd(character, s));
  }

  private void tellRoom(String message)
  {
    CommandDelegate.execute(new SendLocationMessageCmd(ELocation.TAVERN_BACK_ROOM, message, character, true));
  }
}
