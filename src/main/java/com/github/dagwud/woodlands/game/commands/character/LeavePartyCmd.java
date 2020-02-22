package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyAlertCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.CommandDelegate;

public class LeavePartyCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final GameCharacter character;
  private final Party party;

  public LeavePartyCmd(GameCharacter character, Party party)
  {
    super(new AbleToActPrerequisite(character));
    this.character = character;
    this.party = party;
  }

  public LeavePartyCmd(GameCharacter character, Party party, boolean evenIfDead)
  {
    super();
    this.character = character;
    this.party = party;
  }

  @Override
  public void execute()
  {
    ExpireSpellsCmd expireAll = new ExpireSpellsCmd(character.getSpellAbilities().getPassivePartySpells());
    CommandDelegate.execute(expireAll);

    if (!party.isPrivateParty())
    {
      CommandDelegate.execute(new SendPartyMessageCmd(party, character.getName() + " has left " + party.getName()));
    }
    party.removeMember(character);
    CommandDelegate.execute(new SendPartyAlertCmd(party, joiner.getName() + " has left " + party.getName()));
  }

  @Override
  public String toString()
  {
    return "LeavePartyCmd{" +
            "character=" + character +
            ", party=" + party +
            '}';
  }
}
