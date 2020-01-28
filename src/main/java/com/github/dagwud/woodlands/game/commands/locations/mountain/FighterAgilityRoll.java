package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;

class FighterAgilityRoll
{
  private final int agilityRoll;
  private final Fighter fighter;

  FighterAgilityRoll(Fighter fighter)
  {
    this.fighter = fighter;
    DiceRollCmd cmd = new DiceRollCmd(1, 20);
    cmd.execute();
    agilityRoll = cmd.getTotal() + fighter.getStats().getAgility().total() - fighter.getStats().getDrunkeness();
  }

  int getAgilityRoll()
  {
    return agilityRoll;
  }

  public Fighter getFighter()
  {
    return fighter;
  }
}
