package com.github.dagwud.woodlands.game.commands.poker;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.npc.PokerDealer;

/**
 * Used to knock the player out of the game regardless of the current game state, because the player leaves the game for
 * some reason (moves away, stands up, gets rallied etc)
 */
public class HardFoldCmd extends AbstractCmd
{
  private PlayerCharacter playerCharacter;

  public HardFoldCmd(PlayerCharacter playerCharacter)
  {
    super(new NotInPrivatePartyPrerequisite(playerCharacter));
    this.playerCharacter = playerCharacter;
  }

  @Override
  public void execute()
  {
    PokerDealer pokerDealer = playerCharacter.getParty().getPokerDealer();
    pokerDealer.foldOut(playerCharacter);

    if (pokerDealer.isGameOver()) {
      CommandDelegate.execute(new ProcessGameOverCmd(pokerDealer));
    }
  }
}
