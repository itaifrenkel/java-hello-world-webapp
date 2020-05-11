package com.github.dagwud.woodlands.game.commands.poker;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.Command;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.npc.PokerDealer;
import za.co.knonchalant.pokewhat.domain.GameState;
import za.co.knonchalant.pokewhat.domain.Hand;
import za.co.knonchalant.pokewhat.domain.lookup.EGameState;

import java.util.Set;

public class ExplainGameStateCmd extends AbstractCmd
{
  private final PlayerCharacter character;
  private final GameState gameState;

  public ExplainGameStateCmd(PlayerCharacter character, GameState gameState)
  {
    this.character = character;
    this.gameState = gameState;
  }

  @Override
  public void execute()
  {
    String state = translate(gameState.getState());
    if (gameState.getCurrentCards().isEmpty())
    {
      state += " - no table cards yet.";
    }
    else
    {
      state += "\n Table cards: " + gameState.getCurrentCards();
    }

    PokerDealer pokerDealer = character.getParty().getPokerDealer();
    double currentPool = pokerDealer.getCurrentGame().getCurrentPool();
    if (currentPool > 0)
    {
      state += "\nCurrent betting pool: " + currentPool;
    }

    Set<PlayerCharacter> characters = pokerDealer.getPlayers();
    for (PlayerCharacter playerCharacter : characters)
    {
      Hand hand = pokerDealer.getHandFor(playerCharacter);

      String playerState = state + "\n Your cards: " + hand.getCards();
      CommandDelegate.execute(new SendMessageCmd(playerCharacter, playerState));
    }
  }

  private String translate(EGameState state)
  {
    switch (state)
    {
      case DONE:
        return "Game over";
      case RIVER:
        return "Current round: river";
      case TURN:
        return "Current round: turn";

      case FLOP:
        return "Current round: flop";

      case PRE_FLOP:
        return "Current round: pre-flop";

      case BLINDS:
        return "Current round: blinds";

      case NOT_STARTED:
        return "Game not started";

      case WAITING_FOR_PLAYERS:
        return "Waiting for players";
    }
    return "Mysterious.";
  }
}
