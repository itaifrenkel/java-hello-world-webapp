package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyAlertCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.domain.*;

public class DefeatSparringPartnerCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Fighter victor;
  private final Fighter loser;

  public DefeatSparringPartnerCmd(Fighter victor, Fighter loser)
  {
    this.victor = victor;
    this.loser = loser;
  }

  @Override
  public void execute()
  {
    if (victor instanceof PlayerCharacter)
    {
      CommandDelegate.execute(new SendMessageCmd((PlayerCharacter) victor, "<b>You have defeated " + loser.getName() + "!</b>"));
    }
    if (loser instanceof PlayerCharacter)
    {
      CommandDelegate.execute(new SendMessageCmd((PlayerCharacter) loser, "<b>You have been defeated by " + victor.getName() + "!</b>"));
    }

    Party victorParty = determineParty(victor);
    Party loserParty = determineParty(loser);
    if (victorParty != null)
    {
      CommandDelegate.execute(new SendPartyAlertCmd(victorParty, "<b>" + victor.getName() + " has defeated " + loser.getName() + " in a sparring match!</b>"));
      if (victorParty != loserParty && loserParty != null)
      {
        CommandDelegate.execute(new SendPartyAlertCmd(victorParty, "<b>" + loser.getName() + " has been defeated by " + victorParty.getName() + " in a sparring match!</b>"));
      }
    }

    // Defeated character must be taken to the village for resuscitation; victor can go celebrate:
    CommandDelegate.execute(new MoveToLocationCmd((GameCharacter) victor, ELocation.TAVERN));
    CommandDelegate.execute(new MoveToLocationCmd((GameCharacter) loser, ELocation.VILLAGE_SQUARE, true));
  }

  private Party determineParty(Fighter fighter)
  {
    if (fighter instanceof PlayerCharacter)
    {
      PlayerCharacter character = (PlayerCharacter) fighter;
      Party party = character.getParty();
      if (!party.isPrivateParty())
      {
        return party;
      }
    }
    return null;
  }
}
