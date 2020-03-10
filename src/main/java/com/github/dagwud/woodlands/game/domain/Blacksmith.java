package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.gson.game.Weapon;

public class Blacksmith extends Crafter<Weapon>
{
  private static final long serialVersionUID = 1L;

  Blacksmith()
  {
    super(null);
    setName("the Blacksmith");
  }

  @Override
  protected void incrementCollectionStat(PlayerCharacter collectedBy)
  {
    collectedBy.getStats().incrementCraftsCount();

    EEvent.CRAFTED_ITEM.trigger(collectedBy);
  }
}
