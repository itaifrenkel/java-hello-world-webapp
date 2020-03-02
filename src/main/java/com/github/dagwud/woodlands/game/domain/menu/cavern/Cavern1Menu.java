package com.github.dagwud.woodlands.game.domain.menu.cavern;

import com.github.dagwud.woodlands.game.domain.ELocation;

public class Cavern1Menu extends CavernMenu
{
  @Override
  protected ELocation getBackTowardsEntranceLocation()
  {
    return ELocation.CAVERN_ENTRANCE;
  }

  @Override
  protected ELocation getDeeperLocation()
  {
    return ELocation.CAVERN_2;
  }
}
