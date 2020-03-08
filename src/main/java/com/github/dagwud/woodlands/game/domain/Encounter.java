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

  private Party party;
  private List<? extends Fighter> enemies;
  private boolean ended;
  private int currentRound;
  private final int actionsAllowedPerRound;
  private boolean hasAnyPlayerActivityPrepared;

  /**
   * a farmed encounter is one that happened automatically with no player intervention
  */
  private boolean farmed = true;
  private boolean fightingStarted;

  public Encounter(Party party, List<? extends Fighter> enemies)
  {
    this(party, enemies, 3); // two attacks and a spell
  }

  protected Encounter(Party party, List<? extends Fighter> enemies, int actionsAllowedPerRound)
  {
    this.party = party;
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
  }

  public List<? extends Fighter> getEnemies()
  {
    return enemies;
  }

  public Collection<Fighter> getAllFighters()
  {
    Collection<Fighter> fighters = new HashSet<>(party.getActiveMembers());
    fighters.addAll(enemies);
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
    for (PlayerCharacter member : getParty().getActivePlayerCharacters())
    {
      if (member.isConscious())
      {
        return true;
      }
    }
    return false;
  }
}
