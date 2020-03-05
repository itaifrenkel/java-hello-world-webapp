package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.gson.game.Weapon;

import java.util.Map;
import java.util.HashMap;

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
  }
}
