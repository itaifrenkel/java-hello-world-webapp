package com.github.dagwud.woodlands.game.commands.poker;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.EEvent;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.npc.PokerDealer;
import za.co.knonchalant.pokewhat.domain.GameResult;
import za.co.knonchalant.pokewhat.domain.GameState;
import za.co.knonchalant.pokewhat.domain.Player;
import za.co.knonchalant.pokewhat.domain.lookup.EHand;

import java.util.List;
import java.util.Set;

public class ProcessGameOverCmd extends AbstractCmd
{
  private final PokerDealer pokerDealer;
  private final GameState gameState;

  public ProcessGameOverCmd(PokerDealer pokerDealer)
  {
    this.pokerDealer = pokerDealer;
    this.gameState = pokerDealer.getCurrentGame().getGameState();
  }

  @Override
  public void execute()
  {
    List<GameResult> result = pokerDealer.getCurrentGame().getResult();
    StringBuilder message = new StringBuilder("Game over!\n");
    boolean wasAFold = result.get(0).getWinners().values().iterator().next().getHandResult() == EHand.FOLD;

    if (!wasAFold)
    {
      message.append("Table cards: ").append(gameState.getCurrentCards()).append("\n");
      for (PlayerCharacter player : pokerDealer.getPlayers())
      {
        message.append(player.getName()).append(":").append(pokerDealer.getHandFor(player).getCards()).append("\n");
      }
    }

    for (GameResult gameResult : result)
    {
      Set<Player> players = gameResult.getWinners().keySet();
      if (players.size() == 1)
      {
        Player winner = players.iterator().next();
        message.append(winner.getName()).append(" wins the ").append(gameResult.getDescription()).append(" Pot with ").append(gameResult.getWinners().get(winner).getHandResult().getName()).append(" - ");
        message.append(gameResult.getAmountPerPlayer()).append(" in the bank!");
        winner.win(gameResult.getAmountPerPlayer());

        EEvent.WON_POKER.trigger(pokerDealer.playerFor(winner));
      }
      else
      {
        message.append("There's a tie for the ").append(gameResult.getDescription()).append(" Pot: ");
        boolean first = true;
        for (Player player : players)
        {
          if (first)
          {
            first = false;
          }
          else
          {
            message.append(" and ");
          }

          message.append(player.getName());
          player.win(gameResult.getAmountPerPlayer());
        }

        message.append(" win ").append(gameResult.getAmountPerPlayer()).append(" with ").append(gameResult.getWinners().entrySet().iterator().next().getValue().getHandResult().getName());
      }
    }

    tellAll(message.toString());
    pokerDealer.endGame();
  }

  private void tellAll(String message)
  {
    for (PlayerCharacter player : pokerDealer.getPlayers())
    {
      tellPlayer(player, message);
    }
  }

  private void tellPlayer(PlayerCharacter character, String s)
  {
    CommandDelegate.execute(new SendMessageCmd(character, s));
  }
}
