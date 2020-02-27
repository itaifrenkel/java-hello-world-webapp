package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.prerequisites.AbleToActPrerequisite;
import com.github.dagwud.woodlands.game.domain.EEvent;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Party;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

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

    if (character instanceof PlayerCharacter)
    {
      EEvent.LEFT_PARTY.trigger((PlayerCharacter) character);
    }

    party.removeMember(character);

    JoinPartyCmd join = new JoinPartyCmd(character, "_" + character.getName());
    CommandDelegate.execute(join);
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
