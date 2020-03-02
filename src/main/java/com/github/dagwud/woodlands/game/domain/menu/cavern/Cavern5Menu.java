package com.github.dagwud.woodlands.game.domain.menu.cavern;

import com.github.dagwud.woodlands.game.domain.ELocation;

public class Cavern5Menu extends CavernMenu
{
  @Override
  protected ELocation getBackTowardsEntranceLocation()
  {
    return ELocation.CAVERN_4;
  }

  @Override
  ELocation getDeeperLocation()
  {
    return ELocation.CAVERN_7;
  }

  @Override
  protected ELocation getSidePathLocation()
  {
    return ELocation.CAVERN_6;
  }
}
