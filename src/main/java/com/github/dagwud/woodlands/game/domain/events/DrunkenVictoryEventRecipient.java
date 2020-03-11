package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

import java.util.List;

public class DrunkenVictoryEventRecipient implements EventRecipient<CreatureDefeatedEvent>
{
  @Override
  public void trigger(CreatureDefeatedEvent event)
  {
    PlayerCharacter member = event.getPlayerCharacter();

    List<PlayerCharacter> playerCharacters = member.getParty().getActivePlayerCharacters();
    for (PlayerCharacter playerCharacter : playerCharacters)
    {
      if (playerCharacter.getStats().getDrunkeness() == 0 || !playerCharacter.shouldGainExperienceByDefeating(event.getCreature()))
      {
        return;
      }
    }

    // awkward to have to loop twice but I think we have to
    for (PlayerCharacter playerCharacter : playerCharacters)
    {
      CommandDelegate.execute(new UnlockAchievementCmd(playerCharacter, EAchievement.DRUNKEN_VICTORY));
    }
  }
}
