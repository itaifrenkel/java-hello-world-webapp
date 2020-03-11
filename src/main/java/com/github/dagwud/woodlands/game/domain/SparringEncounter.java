package com.github.dagwud.woodlands.game.domain;

import java.util.Collection;
import java.util.HashSet;

public class SparringEncounter extends ManualEncounter
{
  private static final long serialVersionUID = 1L;

  public SparringEncounter(FightingGroup aggressor, FightingGroup enemies, int timeAllowedForPlanningMS, int actionsAllowedPerRound)
  {
    super(aggressor, enemies, timeAllowedForPlanningMS, actionsAllowedPerRound);
  }

  @Override
  public Collection<Fighter> getAllFighters()
  {
    Collection<Fighter> fighters = new HashSet<>();
    fighters.addAll(getAggressor().getActiveMembers());
    fighters.addAll(getEnemies().getActiveMembers());
    return fighters;
  }

  @Override
  public Fighter chooseFighterToAttack(Fighter attacker)
  {
    PlayerCharacter playerAttacker = (PlayerCharacter)attacker;
    return playerAttacker.chooseFighterToAttack(getAllFighters(), true, attacker);
  }

  @Override
  public boolean anyAggressorsStillConscious()
  {
    for (Fighter activeMember : getAggressor().getActiveMembers())
    {
      if (activeMember.isConscious())
      {
        return true;
      }
    }
    return false;
  }
}
