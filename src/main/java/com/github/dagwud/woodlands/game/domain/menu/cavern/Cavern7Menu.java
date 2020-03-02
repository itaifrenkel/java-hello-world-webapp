package com.github.dagwud.woodlands.game.domain.menu.cavern;

import com.github.dagwud.woodlands.game.domain.ELocation;

public class Cavern7Menu extends CavernMenu
{
  @Override
  protected ELocation getBackTowardsEntranceLocation()
  {
    return ELocation.CAVERN_5;
  }

  @Override
  ELocation getDeeperLocation()
  {
    return ELocation.CAVERN_8;
  }

  @Override
  ELocation getSidePathLocation()
  {
    return ELocation.THE_GORGE;
  }
}
