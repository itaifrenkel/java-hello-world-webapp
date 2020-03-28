package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.*;

public class RetreatCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final PlayerCharacter inDanger;

  RetreatCmd(PlayerCharacter inDanger)
  {
    this.inDanger = inDanger;
  }

  @Override
  public void execute()
  {
    GameCharacter retreater = inDanger.isConscious() ? inDanger : findConsciousCharacter(inDanger.getParty());
    String message = buildRetreatMessage(inDanger, retreater);
    SendPartyMessageCmd msg = new SendPartyMessageCmd(inDanger.getParty(), message);
    CommandDelegate.execute(msg);

    EndEncounterCmd end = new EndEncounterCmd(inDanger.getParty().getActiveEncounter());
    CommandDelegate.execute(end);

    MoveToLocationCmd cmd = new MoveToLocationCmd(retreater, ELocation.VILLAGE_SQUARE);
    CommandDelegate.execute(cmd);
  }

  private String buildRetreatMessage(PlayerCharacter inDanger, Fighter retreater)
  {
    Party party = inDanger.getParty();
    if (party.isPrivateParty())
    {
      return "<b>You're getting dangerously hurt, and decide to fall back to the village</b>";
    }
    if (inDanger == retreater)
    {
      return "<i>" + inDanger.getName() + " sounds the retreat; " + party.getName() + " falls back to The Village</i>";
    }
    return "<i>" + inDanger.getName() + " is badly wounded so " + retreater.getName() + " sounds the retreat; " + party.getName() + " falls back to The Village</i>";
  }

  private GameCharacter findConsciousCharacter(Party party)
  {
    ChooseConsciousCharacterCmd choose = new ChooseConsciousCharacterCmd(party);
    CommandDelegate.execute(choose);
    if (choose.getChosen() == null)
    {
      throw new WoodlandsRuntimeException("Nobody is alive to sound the retreat");
    }
    return choose.getChosen();
  }
}
