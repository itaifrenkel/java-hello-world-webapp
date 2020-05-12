package com.github.dagwud.woodlands.game.commands.poker;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.*;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.npc.PokerDealer;
import za.co.knonchalant.pokewhat.domain.Game;
import za.co.knonchalant.pokewhat.domain.GameState;
import za.co.knonchalant.pokewhat.domain.lookup.EBetResult;
import za.co.knonchalant.pokewhat.domain.lookup.EGameState;

public class PlaceBetCmd extends SuspendableCmd
{
  private final PlayerCharacter character;
  private final double amount;

  public PlaceBetCmd(PlayerCharacter character, double amount)
  {
    super(character.getPlayedBy().getPlayerState(), 2, new NotInPrivatePartyPrerequisite(character));
    this.character = character;
    this.amount = amount;
  }

  public PlaceBetCmd(PlayerCharacter character)
  {
    super(character.getPlayedBy().getPlayerState(), 2, new NotInPrivatePartyPrerequisite(character));
    this.character = character;
    this.amount = -1;
  }

  @Override
  protected void executePart(int phaseToExecute, String capturedInput)
  {
    Party party = character.getParty();

    PokerDealer pokerDealer = party.getPokerDealer();
    if (!pokerDealer.playerIsInGame(character))
    {
      // shouldn't happen anyways
      tellPlayer("You can't bet until you're in the game.");
      return;
    }

    Game currentGame = pokerDealer.getCurrentGame();

    if (currentGame.getState() == EGameState.WAITING_FOR_PLAYERS)
    {
      tellPlayer("You'd be betting against yourself - you need someone to play against.");
      return;
    }

    if (currentGame.getState() == EGameState.NOT_STARTED)
    {
      currentGame.start();
      tellPlayer("If not, why not? You propose starting the game and it begins.");
      tellRoom("Game has started!");
    }

    String currentPlayerName = currentGame.getCurrentPlayer().getName();
    if (!currentPlayerName.equals(character.getName()))
    {
      tellPlayer("Nuh uh - it's " + currentPlayerName + "'s turn.");
      return;
    }

    if (amount != -1)
    {
      placeTheBet(pokerDealer, amount);
      removeSuspendable();
      return;
    }

    switch (phaseToExecute)
    {
      case 0:
        String betPrompt = "How much do you want to bet?";
        double currentBet = pokerDealer.getCurrentBetFor(character);

        double amountNeeded = currentGame.getCurrentBet() - currentBet;
        if (amountNeeded > 0)
        {
          betPrompt += " You need at least " + amountNeeded + " to see the hand - anything less will fold.";
        }

        tellPlayer(betPrompt);
        break;

      case 1:
        try
        {
          double amount = Double.parseDouble(capturedInput);
          placeTheBet(pokerDealer, amount);
        }
        catch (NumberFormatException ex)
        {
          tellPlayer("We use real grown-up numbers around here.");
          super.rejectCapturedInput();
        }
        break;
    }
  }

  private void placeTheBet(PokerDealer pokerDealer, double amount)
  {
    EBetResult bet = pokerDealer.bet(character, amount);
    if (bet != EBetResult.OUT_OF_TURN)
    {
      switch (bet)
      {
        case SAW:
          tellPlayer("You see the current bet at " + amount);
          tellRoom(character.getName() + " sees the current bet at " + amount);
          break;
        case RAISED:
          tellPlayer("You raise to " + amount);
          tellRoom(character.getName() + " raises by " + amount);

          break;
        case FOLDED:
          tellPlayer("You fold.");
          tellRoom(character.getName() + " folds.");

          break;
        case CHECKED:
          tellPlayer("You check.");
          tellRoom(character.getName() + " checks.");

          break;

        case ALL_IN:
          tellPlayer("You go all in.");
          tellRoom(character.getName() + " goes all-in!");

          break;
      }
    }

    while (pokerDealer.getCurrentGame().currentRoundDone())
    {
      GameState gameState = pokerDealer.getCurrentGame().nextRound();
      if (gameState.getState() != EGameState.DONE)
      {
        CommandDelegate.execute(new ExplainGameStateCmd(character, gameState));
      }
      else
      {
        processGameOver(pokerDealer, gameState);
        break;
      }
    }

    for (PlayerCharacter player : pokerDealer.getPlayers())
    {
      CommandDelegate.execute(new ShowMenuCmd(player.getLocation().getMenu(), player.getPlayedBy().getPlayerState()));
    }
  }

  private void processGameOver(PokerDealer pokerDealer, GameState gameState)
  {
    CommandDelegate.execute(new ProcessGameOverCmd(pokerDealer));
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
