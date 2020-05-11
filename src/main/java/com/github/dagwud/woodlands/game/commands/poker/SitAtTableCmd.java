package com.github.dagwud.woodlands.game.commands.poker;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendLocationMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.npc.PokerDealer;
import za.co.knonchalant.pokewhat.domain.Game;
import za.co.knonchalant.pokewhat.domain.Player;

public class SitAtTableCmd extends AbstractCmd
{
  private final PlayerCharacter character;

  public SitAtTableCmd(PlayerCharacter character)
  {
    this.character = character;
  }

  @Override
  public void execute() throws Exception
  {
    Party party = character.getParty();
    if (party.isPrivateParty())
    {
      tellPlayer("Can't play poker by yourself - join a party.");
      return;
    }

    PokerDealer pokerDealer = party.getPokerDealer();
    Game currentGame = pokerDealer.getCurrentGame();

    if (currentGame == null)
    {
      tellRoom(character.getName() + " sits down at the empty poker table and looks around for someone to fleece.");
      tellPlayer("You sit down at the empty poker table and look around for someone to join you.");
      pokerDealer.newGame();
    }
    else
    {
      tellRoom(character.getName() + " sits down at the poker table and waits to be dealt in.");
      tellPlayer("You sit down at the poker table and wait to be dealt in.");
    }

    pokerDealer.seatPlayer(character);
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
