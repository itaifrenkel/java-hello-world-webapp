package com.github.dagwud.woodlands.game.domain;

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

  public boolean isEnded()
  {
    return ended;
  }

  public void end()
  {
    ended = true;
  }

  public void setEnemy(Creature enemy)
  {
    this.enemy = enemy;
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
}
