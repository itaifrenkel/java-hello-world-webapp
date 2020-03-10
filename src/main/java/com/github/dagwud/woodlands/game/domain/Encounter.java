package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.battle.AutomaticEncounterRoundCmd;
import com.github.dagwud.woodlands.game.commands.battle.EncounterRoundCmd;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Encounter implements Serializable
{
  private static final long serialVersionUID = 1L;

  private FightingGroup aggressor;
  private List<? extends Fighter> enemies;
  private boolean ended;
  private int currentRound;
  private final int actionsAllowedPerRound;
  private boolean hasAnyPlayerActivityPrepared;

  /**
   * a farmed encounter is one that happened automatically with no player intervention
  */
  private boolean farmed = true;
  private EncounterStatus status;

  public Encounter(FightingGroup aggressor, List<? extends Fighter> enemies)
  {
    this(aggressor, enemies, 3); // two attacks and a spell
  }

  protected Encounter(FightingGroup aggressor, List<? extends Fighter> enemies, int actionsAllowedPerRound)
  {
    this.aggressor = aggressor;
    this.enemies = enemies;
    this.actionsAllowedPerRound = actionsAllowedPerRound;
  }

  public boolean isEnded()
  {
    return ended;
  }

  public void end()
  {
    ended = true;
    Party party;
    if (getAggressor() instanceof Party)
    {
      party = (Party) getAggressor();
    }
    else
    {
      party = getAggressor().getLeader().getParty();
    }
    party.setActiveEncounter(null);
  }

  public List<? extends Fighter> getEnemies()
  {
    return enemies;
  }

  public Collection<Fighter> getAllFighters()
  {
    Collection<Fighter> fighters = new HashSet<>(aggressor.getActiveMembers());
    fighters.addAll(enemies);
    return fighters;
  }

  public FightingGroup getAggressor()
  {
    return aggressor;
  }

  public int getBattleRound()
  {
    return currentRound;
  }

  public void incrementBattleRound()
  {
    currentRound++;
  }

  public boolean isFarmed()
  {
    return farmed;
  }

  public final void markNotFarmed()
  {
    farmed = false;
  }

  public final int getActionsAllowedPerRound()
  {
    return actionsAllowedPerRound;
  }

  public final void setStatus(EncounterStatus status)
  {
    this.status = status;
  }

  public final EncounterStatus getStatus()
  {
    return status;
  }

  public EncounterRoundCmd createNextRoundCmd(PlayerState playerState, int delayBetweenRoundsMS)
  {
    return new AutomaticEncounterRoundCmd(playerState, delayBetweenRoundsMS);
  }

  public void setHasAnyPlayerActivityPrepared(boolean hasAnyPlayerActivityPrepared)
  {
    this.hasAnyPlayerActivityPrepared = hasAnyPlayerActivityPrepared;
  }

  boolean hasAnyPlayerActivityPrepared()
  {
    return hasAnyPlayerActivityPrepared;
  }

  public void startFighting()
  {
  }

  public Fighter chooseFighterToAttack(Fighter attacker)
  {
    return attacker.chooseFighterToAttack(getAllFighters());
  }

  public boolean anyAggressorsStillConscious()
  {
    for (PlayerCharacter member : getAggressor().getActivePlayerCharacters())
    {
      if (member.isConscious())
      {
        return true;
      }
    }
    return false;
  }
}
