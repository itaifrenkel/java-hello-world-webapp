package com.github.dagwud.woodlands.game.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class SparringEncounter extends ManualEncounter
{
  private static final long serialVersionUID = 1L;
  private final PlayerCharacter aggressor;

  public SparringEncounter(Party party, PlayerCharacter aggressor, List<? extends Fighter> enemies, int timeAllowedForPlanningMS, int actionsAllowedPerRound)
  {
    super(party, enemies, timeAllowedForPlanningMS, actionsAllowedPerRound);
    this.aggressor = aggressor;
  }

  @Override
  public Collection<Fighter> getAllFighters()
  {
    Collection<Fighter> fighters = new HashSet<>();
    fighters.add(aggressor);
    fighters.addAll(getEnemies());
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
    return aggressor.isConscious();
  }
}
