package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.characters.Explorer;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;

public class JoinPartyCmd extends AbstractCmd
{
  private final String partyName;
  private final GameCharacter joiner;

  public JoinPartyCmd(GameCharacter joiner, String partyName)
  {
    super(new AbleToActPrerequisite(joiner),
            new AlreadyInPartyPrerequisite(joiner, partyName));
    this.joiner = joiner;
    this.partyName = partyName;
  }

  @Override
  public void execute()
  {
    if (null != joiner.getParty())
    {
      LeavePartyCmd leave = new LeavePartyCmd(joiner, joiner.getParty());
      CommandDelegate.execute(leave);
    }

    Party party = PartyRegistry.lookup(partyName);
    if (!isPartyInTheVillage(party))
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

    for (PartySpell partySpell : joiner.getSpellAbilities().getPartySpells())
    {
      partySpell.cast();
    }
  }

  private boolean isPartyInTheVillage(Party party)
  {
    if (null == party.getLeader())
    {
      return true;
    }
    return party.getLeader().getLocation() == ELocation.VILLAGE_SQUARE
      || party.getLeader().getLocation() == ELocation.INN
      || party.getLeader().getLocation() == ELocation.TAVERN;
  }

}
