package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.Icons;
import com.github.dagwud.woodlands.game.domain.Fighter;

public class BeadsOfPower extends PowerBoostTrinket
{
  private static final int MAX_HP_BOOST = 4;
  private static final long serialVersionUID = 1L;

  @Override
  public String getName()
  {
    return "Beads of Power";
  }

  @Override
  public String getIcon()
  {
    return BEADS_ICON;
  }

  @Override
  int getBoostAmount()
  {
    return MAX_HP_BOOST;
  }

  @Override
  String produceEquipMessage(Fighter fighter)
  {
    return "You feel a rush of power as you put on the beads. Your maximum HP has been boosted to " + "❤" + fighter.getStats().getMaxHitPoints().total();
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return Icons.HP + MAX_HP_BOOST;
  }
}
