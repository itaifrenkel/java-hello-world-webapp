package com.github.dagwud.woodlands.game.domain.events.achievements;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.events.CharacterDroppedItemEvent;
import com.github.dagwud.woodlands.game.domain.events.EventRecipient;

public class CharacterDroppedItemEventRecipient implements EventRecipient<CharacterDroppedItemEvent>
{
  private static final int DROPS_TO_GAIN_ACHIEVEMENT = 20;

  @Override
  public void trigger(CharacterDroppedItemEvent event)
  {
    PlayerCharacter member = event.getPlayerCharacter();
    member.getStats().incrementItemsDroppedCount();
    if (member.getStats().getItemsDroppedCount() == DROPS_TO_GAIN_ACHIEVEMENT)
    {
      CommandDelegate.execute(new UnlockAchievementCmd(member, EAchievement.LITTERER));
    }
  }
}
