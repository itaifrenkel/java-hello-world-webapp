package com.github.dagwud.woodlands.game.domain.menu.cavern;

import com.github.dagwud.woodlands.game.domain.ELocation;

public class Cavern8Menu extends CavernMenu
{
  @Override
  protected ELocation getBackTowardsEntranceLocation()
  {
    return ELocation.CAVERN_7;
  }

  @Override
  ELocation getDeeperLocation()
  {
    // The is the deepest point of the cave system. Future cavern expansion is possible from here...
    return null;
  }

  @Override
  ELocation getSidePathLocation()
  {
    return null;
  }
}
