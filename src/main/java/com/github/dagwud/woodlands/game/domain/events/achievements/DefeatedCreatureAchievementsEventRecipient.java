package com.github.dagwud.woodlands.game.domain.events.achievements;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.events.CreatureDefeatedEvent;
import com.github.dagwud.woodlands.game.domain.events.EventRecipient;

import java.util.List;

public class DefeatedCreatureAchievementsEventRecipient implements EventRecipient<CreatureDefeatedEvent>
{
  @Override
  public void trigger(CreatureDefeatedEvent event)
  {
    PlayerCharacter member = event.getPlayerCharacter();

    checkForKillCountAchievements(member);

    checkForDrunkenVictory(event, member);
  }

  private void checkForDrunkenVictory(CreatureDefeatedEvent event, PlayerCharacter member)
  {
    List<PlayerCharacter> activeMembers = member.getParty().getActivePlayerCharacters();

    for (PlayerCharacter activeMember : activeMembers)
    {
      if (activeMember.getStats().getDrunkeness() == 0 || !activeMember.shouldGainExperienceByDefeating(event.getCreature()))
      {
        return;
      }
    }

    // awkward to have to loop twice but I think we have to
    for (PlayerCharacter activeMember : activeMembers)
    {
      CommandDelegate.execute(new UnlockAchievementCmd(activeMember, EAchievement.DRUNKEN_VICTORY));
    }
  }

  private void checkForKillCountAchievements(PlayerCharacter member)
  {
    if (member.getStats().getCreaturesDefeatedCount() == 1)
    {
      CommandDelegate.execute(new UnlockAchievementCmd(member, EAchievement.FIRST_BLOOD));
    }
    else if (member.getStats().getCreaturesDefeatedCount() == 100)
    {
      CommandDelegate.execute(new UnlockAchievementCmd(member, EAchievement.BLOOD_BATH));
    }
  }
}
