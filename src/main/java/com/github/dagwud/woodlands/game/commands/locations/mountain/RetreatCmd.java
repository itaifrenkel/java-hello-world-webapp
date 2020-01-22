package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class RetreatCmd extends AbstractCmd
{
  private final GameCharacter inDanger;

  RetreatCmd(GameCharacter inDanger)
  {
    this.inDanger = inDanger;
  }

  @Override
  public void execute()
  {
    String message = buildRetreatMessage();
    SendPartyMessageCmd msg = new SendPartyMessageCmd(inDanger.getParty(), message);
    CommandDelegate.execute(msg);

    EndEncounterCmd end = new EndEncounterCmd(inDanger.getParty().getActiveEncounter());
    CommandDelegate.execute(end);

    MoveToLocationCmd cmd = new MoveToLocationCmd(inDanger, ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(cmd);
  }

  private String buildRetreatMessage()
  {
    if (inDanger.getParty().isPrivateParty())
    {
      return "You're getting dangerously hurt, and decide to fall back to the village";
    }
    return inDanger.getName() + " sounds the retreat; " + inDanger.getParty().getName() + " falls back to The Village";
  }
}
