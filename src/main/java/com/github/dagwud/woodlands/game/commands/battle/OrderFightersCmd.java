package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class OrderFightersCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Collection<Fighter> unOrdered;
  private List<Fighter> orderedFighters;

  public OrderFightersCmd(Collection<Fighter> fighters)
  {
    this.unOrdered = fighters;
  }

  @Override
  public void execute()
  {
    List<FighterAgilityRoll> order = new ArrayList<>(unOrdered.size());
    for (Fighter fighter : unOrdered)
    {
      FighterAgilityRoll r = new FighterAgilityRoll(fighter);
      order.add(r);
    }
    order.sort(Comparator.comparingInt(FighterAgilityRoll::getAgilityRoll).reversed());

    orderedFighters = new ArrayList<>(order.size());
    for (FighterAgilityRoll o : order)
    {
      orderedFighters.add(o.getFighter());
    }
  }

  public List<Fighter> getOrderedFighters()
  {
    return orderedFighters;
  }

  static class FighterAgilityRoll
  {
    private final int agilityRoll;
    private final Fighter fighter;

    FighterAgilityRoll(Fighter fighter)
    {
      this.fighter = fighter;
      if (fighter.canAct())
      {
        DiceRollCmd cmd = new DiceRollCmd(1, 20);
        cmd.execute();
        agilityRoll = cmd.getTotal() + fighter.getStats().getAgility().total() - fighter.getStats().getDrunkeness();
      }
      else
      {
        agilityRoll = -999;
      }
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
}
