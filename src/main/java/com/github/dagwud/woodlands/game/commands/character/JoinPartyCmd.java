package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.GameStatesRegistry;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

public class JoinPartyCmd extends AbstractCmd
{
  private final String partyName;
  private final GameCharacter joiner;

  public JoinPartyCmd(GameCharacter joiner, String partyName)
  {
    this.joiner = joiner;
    this.partyName = partyName;
  }

  @Override
  public void execute()
  {
    Party party = PartyRegistry.lookup(partyName);
    joiner.setParty(party);
    party.getMembers().add(joiner);

    SendPartyMessageCmd welcome = new SendPartyMessageCmd(party, joiner.getName() + " has joined " + partyName + "!");
    CommandDelegate.execute(welcome);
  }
}
