package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.LeavePartyCmd;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.Peasant;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.General;

public class ArmyOfPeasantsFleeRecipient implements EventRecipient<Event>
{
  @Override
  public void trigger(Event event)
  {
    PlayerCharacter playerCharacter = event.getPlayerCharacter();

    if (!(playerCharacter instanceof General))
    {
      return;
    }

    General character = (General) playerCharacter;
    if (character.countAlivePeasants() == 0)
    {
      return;
    }

    CommandDelegate.execute(new SendPartyMessageCmd(playerCharacter.getParty(), "General " + playerCharacter.getName() + " has died, and their army of peasants quickly flees!"));

    for (Peasant peasant : character.getPeasants())
    {
      CommandDelegate.execute(new LeavePartyCmd(peasant, peasant.getParty()));
    }

    character.getPeasants().clear();
  }
}
