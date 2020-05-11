package com.github.dagwud.woodlands.game.domain.npc;

import com.github.dagwud.woodlands.game.domain.EEvent;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class Blacksmith extends Crafter<Weapon>
{
  private static final long serialVersionUID = 1L;

  public Blacksmith()
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
