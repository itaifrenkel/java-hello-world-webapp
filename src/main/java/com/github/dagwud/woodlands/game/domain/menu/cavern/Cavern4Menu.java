package com.github.dagwud.woodlands.game.domain.menu.cavern;

import com.github.dagwud.woodlands.game.domain.ELocation;

public class Cavern4Menu extends CavernMenu
{
  @Override
  protected ELocation getBackTowardsEntranceLocation()
  {
    return ELocation.CAVERN_3;
  }

  @Override
  ELocation getDeeperLocation()
  {
    return ELocation.CAVERN_5;
  }

  @Override
  protected ELocation getSidePathLocation()
  {
    return ELocation.CAVERN_1;
  }
}
