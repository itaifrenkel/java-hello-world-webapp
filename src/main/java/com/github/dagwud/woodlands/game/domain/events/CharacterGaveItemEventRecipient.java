package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class CharacterGaveItemEventRecipient implements EventRecipient<CharacterGaveItemEvent>
{
  private static final int GIFTS_TO_GAIN_ACHIEVEMENT = 20;

  @Override
  public void trigger(CharacterGaveItemEvent event)
  {
    PlayerCharacter member = event.getPlayerCharacter();
    member.getStats().incrementItemsGivenAwayCount();
    if (member.getStats().getItemsGivenAwayCount() == GIFTS_TO_GAIN_ACHIEVEMENT)
    {
      CommandDelegate.execute(new UnlockAchievementCmd(member, EAchievement.PHILANTHROPIST));
    }
  }
}
