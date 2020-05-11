package com.github.dagwud.woodlands.game.domain.npc;

import com.github.dagwud.woodlands.game.domain.EEvent;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.items.EquippableItem;

public class Alchemist extends Crafter<EquippableItem>
{
  private static final long serialVersionUID = 1L;

  public Alchemist()
  {
    super(null);
    setName("the Alchemist");
  }

  @Override
  protected void incrementCollectionStat(PlayerCharacter collectedBy)
  {
    collectedBy.getStats().incrementEnchantmentsCount();

    EEvent.ENCHANTED_ITEM.trigger(collectedBy);
  }
}
