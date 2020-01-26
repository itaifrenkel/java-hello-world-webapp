package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.domain.characters.Explorer;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

public class LeavePartyCmd extends AbstractCmd
{
  private final GameCharacter character;
  private final Party party;

  LeavePartyCmd(GameCharacter character, Party party)
  {
    super(new AbleToActPrerequisite(character));
    this.character = character;
    this.party = party;
  }

  @Override
  public void execute()
  {
    for (PartySpell partySpell : character.getSpellAbilities().getPartySpells())
    {
      partySpell.expire();
    }

    if (!party.isPrivateParty())
    {
      CommandDelegate.execute(new SendPartyMessageCmd(party, character.getName() + " has left " + party.getName()));
    }
    party.removeMember(character);    
  }

}
