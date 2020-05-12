package com.github.dagwud.woodlands.game.domain.npc;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import za.co.knonchalant.pokewhat.domain.Game;
import za.co.knonchalant.pokewhat.domain.Hand;
import za.co.knonchalant.pokewhat.domain.Player;
import za.co.knonchalant.pokewhat.domain.lookup.EBetResult;
import za.co.knonchalant.pokewhat.domain.lookup.EGameState;

import java.util.*;

public class PokerDealer extends NonPlayerCharacter
{
  private static final double BLIND = 1;

  private za.co.knonchalant.pokewhat.domain.Game currentGame;
  private final Map<PlayerCharacter, Double> cash;
  private final Map<PlayerCharacter, Player> inCurrentGame;

  public PokerDealer()
  {
    super(null);
    cash = new HashMap<>();
    inCurrentGame = new HashMap<>();
  }

  public Game getCurrentGame()
  {
    return currentGame;
  }

  public void newGame()
  {
    if (currentGame == null)
    {
      buildGame();
    }
  }

  private synchronized void buildGame()
  {
    if (currentGame == null)
    {
      currentGame = new Game(BLIND);
    }
  }

  public void seatPlayer(PlayerCharacter character)
  {
    if (inCurrentGame.containsKey(character))
    {
      return;
    }

    if (!cash.containsKey(character))
    {
      cash.put(character, 50d);
    }

    Player player = new Player(character.getName(), cash.get(character));
    currentGame.addPlayer(player);
    inCurrentGame.put(character, player);

    if (currentGame.getState() == EGameState.WAITING_FOR_PLAYERS)
    {
      CommandDelegate.execute(new SendPartyMessageCmd(character.getParty(), "Waiting for players at the poker table!"));
    }
    else if (currentGame.getState() == EGameState.NOT_STARTED)
    {
      CommandDelegate.execute(new SendPartyMessageCmd(character.getParty(), "Poker game starting shortly."));
    }

    for (PlayerCharacter menuPlayer : getPlayers())
    {
      CommandDelegate.execute(new ShowMenuCmd(menuPlayer.getLocation().getMenu(), menuPlayer.getPlayedBy().getPlayerState()));
    }
  }

  public boolean playerIsInGame(PlayerCharacter activeCharacter)
  {
    return inCurrentGame.containsKey(activeCharacter);
  }

  public EBetResult bet(PlayerCharacter character, double amount)
  {
    if (currentPlayerIs(character))
    {
      return currentGame.bet(currentGame.getCurrentPlayer(), amount);
    }

    return EBetResult.OUT_OF_TURN;
  }

  public boolean currentPlayerIs(PlayerCharacter activeCharacter)
  {
    Player currentPlayer = currentGame.getCurrentPlayer();

    if (currentPlayer == null)
    {
      return false;
    }

    return currentPlayer.getName().equals(activeCharacter.getName());
  }

  public Set<PlayerCharacter> getPlayers()
  {
    return inCurrentGame.keySet();
  }

  public Hand getHandFor(PlayerCharacter playerCharacter)
  {
    return currentGame.handFor(inCurrentGame.get(playerCharacter));
  }

  public void endGame()
  {
    for (Player player : currentGame.getBets().keySet())
    {
      for (Map.Entry<PlayerCharacter, Player> woodToPoker : inCurrentGame.entrySet())
      {
        if (woodToPoker.getValue().equals(player))
        {
          cash.put(woodToPoker.getKey(), player.getMoney());
        }
      }
    }

    currentGame = null;
    inCurrentGame.clear();
  }

  public double getCurrentBetFor(PlayerCharacter character)
  {
    return currentGame.getBets().get(inCurrentGame.get(character));
  }

  public Map<PlayerCharacter, Double> getCash()
  {
    return cash;
  }

  public void foldOut(PlayerCharacter playerCharacter)
  {
    if (!inCurrentGame.containsKey(playerCharacter))
    {
      return;
    }

    tellPlayers(playerCharacter.getName() + " folds, just like that.");
    currentGame.fold(inCurrentGame.get(playerCharacter));
  }

  private void tellPlayers(String message)
  {
    for (PlayerCharacter player : getPlayers())
    {
      CommandDelegate.execute(new SendMessageCmd(player, message));
    }
  }

  public boolean isGameOver()
  {
    return currentGame.getState() == EGameState.DONE;
  }

  public PlayerCharacter playerFor(Player winner)
  {
    for (Map.Entry<PlayerCharacter, Player> playerCharacterPlayerEntry : inCurrentGame.entrySet())
    {
      if (playerCharacterPlayerEntry.getValue() == winner)
      {
        return playerCharacterPlayerEntry.getKey();
      }
    }

    return null;
  }
}
