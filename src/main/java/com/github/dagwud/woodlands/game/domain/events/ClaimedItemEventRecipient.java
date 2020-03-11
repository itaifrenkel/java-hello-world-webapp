package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendPartyMessageCmd;
import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class ClaimedItemEventRecipient implements EventRecipient<CharacterItemEvent>
{
  @Override
  public void trigger(CharacterItemEvent event)
  {
    PlayerCharacter player = event.getPlayerCharacter();
    Item claimed = event.getItem();

    player.getStats().incrementItemsClaimedCount();
    CommandDelegate.execute(new SendPartyMessageCmd(player.getParty(), String.format("<b>%s has claimed %s</b>", player.getName(), claimed.summary(player))));
  }
}
