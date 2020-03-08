package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.items.EquippableItem;

public class Alchemist extends Crafter<EquippableItem>
{
  private static final long serialVersionUID = 1L;

  Alchemist()
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
