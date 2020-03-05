package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.gson.game.Weapon;
import com.github.dagwud.woodlands.game.items;

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
  }
}
