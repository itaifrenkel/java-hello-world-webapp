package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;

public class CreatureDroppedItemEvent extends Event
{
  private Creature creature;
  private Item item;

  public CreatureDroppedItemEvent(PlayerCharacter fighter, Creature creature, Item item)
  {
    super(fighter);
    this.creature = creature;
    this.item = item;
  }

  public Creature getCreature()
  {
    return creature;
  }

  public Item getItem()
  {
    return item;
  }
}
