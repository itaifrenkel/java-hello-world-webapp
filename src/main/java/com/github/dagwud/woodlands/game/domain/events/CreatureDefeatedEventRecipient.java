package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;

public class CreatureDefeatedEventRecipient implements EventRecipient<CreatureDefeatedEvent>
{
  private static final int COUNT_RECENTLY_DEFEATED_TO_SHOW = 4;

  @Override
  public void trigger(CreatureDefeatedEvent event)
  {
    PlayerCharacter member = event.getPlayerCharacter();
    Creature creatureDefeated = event.getCreature();

    SendMessageCmd msg = new SendMessageCmd(member, creatureDefeated.name + " has been defeated! You gain " + event.getRewardPerCharacter() + " experience");
    CommandDelegate.execute(msg);

    member.getRecentlyDefeated().add(creatureDefeated);
    while (member.getRecentlyDefeated().size() > COUNT_RECENTLY_DEFEATED_TO_SHOW)
    {
      member.getRecentlyDefeated().remove(0);
    }
  }
}
