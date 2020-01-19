package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.gson.game.Creature;

public class Encounter
{
  private GameCharacter host;
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

  public GameCharacter getHost()
  {
    return host;
  }

  public void setHost(GameCharacter host)
  {
    this.host = host;
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
