package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;

public class CreatureDefeatedEvent extends Event
{
  private Creature creature;
  private int rewardPerCharacter;

  public CreatureDefeatedEvent(PlayerCharacter fighter, Creature creature, int rewardPerCharacter)
  {
    super(fighter);
    this.creature = creature;
    this.rewardPerCharacter = rewardPerCharacter;
  }

  public Creature getCreature()
  {
    return creature;
  }

  public int getRewardPerCharacter()
  {
    return rewardPerCharacter;
  }
}
