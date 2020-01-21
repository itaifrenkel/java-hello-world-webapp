package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;

public class LeavePartyCmd extends AbstractCmd
{
  private final GameCharacter character;
  private final Party party;

  public LeavePartyCmd(GameCharacter character, Party party)
  {
    this.character = character;
    this.party = party;
  }

  @Override
  public void execute()
  {
    if (!party.isPrivateParty())
    {
      new SendPartyMessageCmd(party, character.getName() + " has left " + party.getName());
    }
    party.getMembers().remove(character);
    character.setParty(null);
  }

}
