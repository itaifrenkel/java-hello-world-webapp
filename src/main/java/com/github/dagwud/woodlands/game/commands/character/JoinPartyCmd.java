package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.PartyRegistry;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyAlertCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.characters.spells.PassivePartySpell;
import com.github.dagwud.woodlands.game.log.Logger;

public class JoinPartyCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

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
    Logger.info(joiner.getName() + " joining " + partyName);

    if (null != joiner.getParty())
    {
      LeavePartyCmd leave = new LeavePartyCmd(joiner, joiner.getParty());
      CommandDelegate.execute(leave);
    }

    Party party = PartyRegistry.lookup(partyName);
    if (joiner instanceof PlayerCharacter)
    {
      PlayerCharacter character = (PlayerCharacter) joiner;
      if (!isPartyInTheVillage(party))
      {
        SendMessageCmd send = new SendMessageCmd(character, "You can't join that party - it's not in the Village");
        CommandDelegate.execute(send);

        SendPartyMessageCmd partyMsg = new SendPartyMessageCmd(party, character.getName() + " wants to join, but can only do so when you're in the Village");
        CommandDelegate.execute(partyMsg);
        return;
      }
    }

    joiner.setParty(party);
    party.addMember(joiner);

    if (joiner instanceof PlayerCharacter)
    {
      CommandDelegate.execute(new SendPartyAlertCmd(party, joiner.getName() + " has joined " + party.getName()));
    }

    if (party.getLeader() != null)
    {
      // Unless you're the founder
      joiner.setLocation(party.getLeader().getLocation());
    }

    if (!party.isPrivateParty())
    {
      SendPartyMessageCmd welcome = new SendPartyMessageCmd(party, joiner.getName() + " has joined " + partyName + "!");
      CommandDelegate.execute(welcome);
    }

    for (PassivePartySpell passivePartySpell : joiner.getSpellAbilities().getPassivePartySpells())
    {
      CommandDelegate.execute(new CastSpellCmd(passivePartySpell));
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

  @Override
  public String toString()
  {
    return "JoinPartyCmd{" +
            "partyName='" + partyName + '\'' +
            ", joiner=" + joiner +
            '}';
  }
}
