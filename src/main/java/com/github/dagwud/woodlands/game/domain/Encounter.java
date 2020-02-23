package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.battle.AutomaticEncounterRoundCmd;
import com.github.dagwud.woodlands.game.commands.battle.EncounterRoundCmd;
import com.github.dagwud.woodlands.gson.game.Creature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Encounter implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Party party;
  private Creature enemy;
  private boolean ended;
  private int currentRound;
  private final int actionsAllowedPerRound;
  private boolean hasAnyPlayerActivityPrepared;

  /**
   * a farmed encounter is one that happened automatically with no player intervention
  */
  private boolean farmed = true;
  private boolean fightingStarted;

  public Encounter(Party party, Creature enemy)
  {
    this(party, enemy, 3); // two attacks and a spell
  }

  protected Encounter(Party party, Creature enemy, int actionsAllowedPerRound)
  {
    this.party = party;
    this.enemy = enemy;
    this.actionsAllowedPerRound = actionsAllowedPerRound;
  }

  public boolean isEnded()
  {
    return ended;
  }

  public void end()
  {
    ended = true;
  }

  public Creature getEnemy()
  {
    return enemy;
  }

  public Collection<Fighter> getAllFighters()
  {
    Collection<Fighter> fighters = new ArrayList<>(party.getActiveMembers());
    fighters.add(enemy);
    return fighters;
  }

  public Party getParty()
  {
    return party;
  }

  public void setParty(Party party)
  {
    this.party = party;
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

  public final void setFightingStarted(boolean fightingStarted)
  {
    this.fightingStarted = fightingStarted;
  }

  public final boolean hasFightingStarted()
  {
    return fightingStarted;
  }

  public EncounterRoundCmd createNextRoundCmd(PlayerState playerState, int delayBetweenRoundsMS)
  {
    return new AutomaticEncounterRoundCmd(playerState, delayBetweenRoundsMS);
  }

  public void setHasAnyPlayerActivityPrepared(boolean hasAnyPlayerActivityPrepared)
  {
    this.hasAnyPlayerActivityPrepared = hasAnyPlayerActivityPrepared;
  }

  public boolean hasAnyPlayerActivityPrepared()
  {
    return hasAnyPlayerActivityPrepared;
  }

  public void startFighting()
  {
  }
}
