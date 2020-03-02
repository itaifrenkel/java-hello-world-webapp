package com.github.dagwud.woodlands.game.domain.menu.cavern;

import com.github.dagwud.woodlands.game.domain.ELocation;

public class Cavern3Menu extends CavernMenu
{
  @Override
  protected ELocation getBackTowardsEntranceLocation()
  {
    return ELocation.CAVERN_2;
  }

  @Override
  ELocation getDeeperLocation()
  {
    return ELocation.CAVERN_4;
  }
}
