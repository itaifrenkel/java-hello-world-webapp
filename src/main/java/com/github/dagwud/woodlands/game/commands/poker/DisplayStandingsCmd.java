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

import java.util.Map;
import java.util.TreeMap;

public class DisplayStandingsCmd extends AbstractCmd
{
  private final PlayerCharacter character;

  public DisplayStandingsCmd(PlayerCharacter character)
  {
    super(new NotInPrivatePartyPrerequisite(character));
    this.character = character;
  }

  @Override
  public void execute() throws Exception
  {
    Party party = character.getParty();

    PokerDealer pokerDealer = party.getPokerDealer();

    Map<PlayerCharacter, Double> cash = pokerDealer.getCash();

    StringBuilder stringBuilder = new StringBuilder("*Current cash*");
    for (Map.Entry<PlayerCharacter, Double> playerCharacterDoubleEntry : cash.entrySet())
    {
      stringBuilder.append("\n").append(playerCharacterDoubleEntry.getKey().getName()).append(": ").append(playerCharacterDoubleEntry.getValue());
    }

    tellPlayer(stringBuilder.toString());
  }

  private void tellPlayer(String s)
  {
    CommandDelegate.execute(new SendMessageCmd(character, s));
  }
}
