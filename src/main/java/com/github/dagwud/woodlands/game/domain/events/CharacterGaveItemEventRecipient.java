package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.UnlockAchievementCmd;
import com.github.dagwud.woodlands.game.domain.EAchievement;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class CharacterGaveItemEventRecipient implements EventRecipient<CharacterGaveItemEvent>
{
  @Override
  public void trigger(CharacterGaveItemEvent event)
  {
    PlayerCharacter member = event.getPlayerCharacter();
    member.getStats().incrementItemsGivenAwayCount();
    if (member.getStats().getItemsGivenAwayCount() == 4)
    {
      CommandDelegate.execute(new UnlockAchievementCmd(member, EAchievement.PHILANTHROPIST));
    }
  }
}
