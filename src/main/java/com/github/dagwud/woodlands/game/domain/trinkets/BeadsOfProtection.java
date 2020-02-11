package com.github.dagwud.woodlands.game.domain.trinkets;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.characters.spells.AuraOfProtection;

public class BeadsOfProtection extends PowerBoostTrinket
{
  private static final int DEFENCE_BOOST = AuraOfProtection.BUFF_AMOUNT;
  private static final long serialVersionUID = 1L;

  @Override
  public String getName()
  {
    return "Beads of Protection";
  }

  @Override
  public String getIcon()
  {
    return BEADS_ICON;
  }

  @Override
  int getBoostAmount()
  {
    return DEFENCE_BOOST;
  }

  @Override
  String produceEquipMessage(Fighter fighter)
  {
    return "You feel a rush of power as you put on the beads. Your defence has been boosted by 🛡️" + DEFENCE_BOOST;
  }

  @Override
  public String statsSummary(Fighter carrier)
  {
    return "\uD83D\uDEE1" + DEFENCE_BOOST;
  }
}
