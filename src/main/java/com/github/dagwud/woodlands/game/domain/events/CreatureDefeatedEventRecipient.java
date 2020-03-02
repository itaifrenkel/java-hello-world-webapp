package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;

public class CreatureDefeatedEventRecipient implements EventRecipient<CreatureDefeatedEvent>
{
  @Override
  public void trigger(CreatureDefeatedEvent event)
  {
    PlayerCharacter member = event.getPlayerCharacter();
    Creature creatureDefeated = event.getCreature();

    SendMessageCmd msg = new SendMessageCmd(member, creatureDefeated.name + " has been defeated! You gain " + event.getRewardPerCharacter() + " experience");
    CommandDelegate.execute(msg);

    member.getRecentlyDefeated().add(creatureDefeated);
    while (member.getRecentlyDefeated().size() > 10)
    {
      member.getRecentlyDefeated().remove(0);
    }
  }
}
