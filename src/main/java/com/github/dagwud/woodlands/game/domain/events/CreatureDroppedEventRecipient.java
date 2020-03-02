package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;

public class CreatureDroppedEventRecipient implements EventRecipient<CreatureDroppedItemEvent>
{
  @Override
  public void trigger(CreatureDroppedItemEvent event)
  {
    CommandDelegate.execute(new SendMessageCmd(event.getPlayerCharacter(), "<b>" + event.getCreature().name + " dropped a " + event.getItem().getName() + "!</b>"));
  }
}
