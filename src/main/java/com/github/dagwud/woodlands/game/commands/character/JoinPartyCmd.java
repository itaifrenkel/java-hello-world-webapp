package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
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
    if (null != joiner.getParty())
    {
      if (joiner.getParty().getName().equals(partyName))
      {
        SendMessageCmd send = new SendMessageCmd(joiner.getPlayedBy().getChatId(), "You're already in that party!");
        CommandDelegate.execute(send);
        return;
      }
      
      LeavePartyCmd leave = new LeavePartyCmd(joiner, joiner.getParty());
      CommandDelegate.execute(leave);
    }

    Party party = PartyRegistry.lookup(partyName);
    if (null != party.getLeader() && party.getLeader().getLocation() != ELocation.VILLAGE_SQUARE)
    {
      SendMessageCmd send = new SendMessageCmd(joiner.getPlayedBy().getChatId(), "You can't join that party - it's not in the Village");
      CommandDelegate.execute(send);

      SendPartyMessageCmd partyMsg = new SendPartyMessageCmd(party, joiner.getName() + " wants to join, but can only do so when you're in the Village");
      CommandDelegate.execute(partyMsg);
      return;
    }
    joiner.setParty(party);
    party.addMember(joiner);

    if (!party.isPrivateParty())
    {
      SendPartyMessageCmd welcome = new SendPartyMessageCmd(party, joiner.getName() + " has joined " + partyName + "!");
      CommandDelegate.execute(welcome);
    }
  }

}
