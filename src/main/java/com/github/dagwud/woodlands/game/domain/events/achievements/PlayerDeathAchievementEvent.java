package com.github.dagwud.woodlands.game.domain.events.achievements;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.domain.*;
import com.github.dagwud.woodlands.game.domain.events.Event;
import com.github.dagwud.woodlands.game.domain.events.EventRecipient;

public class PlayerDeathAchievementEvent implements EventRecipient<Event>
{
  @Override
  public void trigger(Event event)
  {
    CommandDelegate.execute(new UnlockAchievementCmd(event.getPlayerCharacter(), EAchievement.SHUFFLED_OFF_THE_MORTAL_COIL));

    Party party = event.getPlayerCharacter().getParty();
    Fighter leader = party.getLeader();
    if (leader != event.getPlayerCharacter() && leader instanceof PlayerCharacter)
    {
      CommandDelegate.execute(new UnlockAchievementCmd((PlayerCharacter) leader, EAchievement.EVERYONE_FOR_THEMSELVES));
    }

    if (!party.isPrivateParty() && leader instanceof PlayerCharacter)
    {
      boolean allDead = true;

      for (PlayerCharacter activePlayerCharacter : party.getActivePlayerCharacters())
      {
        if (!activePlayerCharacter.isDead())
        {
          allDead = false;
          break;
        }
      }

      if (allDead)
      {
        CommandDelegate.execute(new UnlockAchievementCmd((PlayerCharacter) leader, EAchievement.PARTY_IS_OVER));
      }
    }
  }
}
