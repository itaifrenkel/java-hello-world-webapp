package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

public class CharacterItemEvent extends Event
{
  private Item item;

  public CharacterItemEvent(PlayerCharacter fighter, Item item)
  {
    super(fighter);
    this.item = item;
  }

  public Item getItem()
  {
    return item;
  }

}
