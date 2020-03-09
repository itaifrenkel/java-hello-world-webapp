package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class CharacterLeftItemEventRecipient implements EventRecipient<Event>
{
  private static final int LEAVINGS_TO_GAIN_ACHIEVEMENT = 20;

  @Override
  public void trigger(Event event)
  {
    PlayerCharacter playerCharacter = event.getPlayerCharacter();

    if (EAchievement.HOARDER.heldBy(playerCharacter))
    {
      return;
    }

    int totalCarried = playerCharacter.getInnkeeper().getCarrying().countTotalCarried();
    if (totalCarried > LEAVINGS_TO_GAIN_ACHIEVEMENT)
    {
      CommandDelegate.execute(new UnlockAchievementCmd(playerCharacter, EAchievement.HOARDER));
    }
  }
}
