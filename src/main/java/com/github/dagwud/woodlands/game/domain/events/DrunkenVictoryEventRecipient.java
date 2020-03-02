package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.List;

public class DrunkenVictoryEventRecipient implements EventRecipient<CreatureDefeatedEvent>
{
  @Override
  public void trigger(CreatureDefeatedEvent event)
  {
    PlayerCharacter member = event.getPlayerCharacter();

    List<GameCharacter> activeMembers = member.getParty().getActiveMembers();

    for (GameCharacter activeMember : activeMembers)
    {
      if (!(activeMember instanceof PlayerCharacter))
      {
        continue;
      }

      if (activeMember.getStats().getDrunkeness() == 0 || !((PlayerCharacter) activeMember).shouldGainExperienceByDefeating(event.getCreature()))
      {
        return;
      }
    }

    // awkward to have to loop twice but I think we have to
    for (GameCharacter activeMember : activeMembers)
    {
      if (!(activeMember instanceof PlayerCharacter))
      {
        continue;
      }

      CommandDelegate.execute(new UnlockAchievementCmd((PlayerCharacter) activeMember, EAchievement.DRUNKEN_VICTORY));
    }
  }
}
