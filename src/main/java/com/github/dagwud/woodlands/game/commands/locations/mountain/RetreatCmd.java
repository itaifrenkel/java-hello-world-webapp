package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.*;

public class RetreatCmd extends AbstractCmd
{
  private final PlayerCharacter inDanger;

  RetreatCmd(PlayerCharacter inDanger)
  {
    this.inDanger = inDanger;
  }

  @Override
  public void execute()
  {
    GameCharacter retreater = inDanger.isConscious() ? inDanger : findActiveCharacter(inDanger.getParty());
    String message = buildRetreatMessage(inDanger, retreater);
    SendPartyMessageCmd msg = new SendPartyMessageCmd(inDanger.getParty(), message);
    CommandDelegate.execute(msg);

    EndEncounterCmd end = new EndEncounterCmd(inDanger.getParty().getActiveEncounter());
    CommandDelegate.execute(end);

    MoveToLocationCmd cmd = new MoveToLocationCmd(retreater, ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(cmd);
  }

  private String buildRetreatMessage(PlayerCharacter inDanger, GameCharacter retreater)
  {
    Party party = inDanger.getParty();
    if (party.isPrivateParty())
    {
      return "You're getting dangerously hurt, and decide to fall back to the village";
    }
    if (inDanger == retreater)
    {
      return inDanger.getName() + " sounds the retreat; " + party.getName() + " falls back to The Village";
    }
    return inDanger.getName() + " is badly wounded so " + retreater.getName() + " sounds the retreat; " + party.getName() + " falls back to The Village";
  }

  private GameCharacter findActiveCharacter(Party party)
  {
    for (GameCharacter member : party.getActiveMembers())
    {
      if (member.isConscious())
      {
        return member;
      }
    }
    throw new WoodlandsRuntimeException("Nobody is alive to sound the retreat");
  }
}
