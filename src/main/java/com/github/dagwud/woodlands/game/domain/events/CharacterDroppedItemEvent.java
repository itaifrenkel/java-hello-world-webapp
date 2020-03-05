package com.github.dagwud.woodlands.game.domain.events;

import com.github.dagwud.woodlands.game.domain.Item;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;

public class CharacterDroppedItemEvent extends Event
{
  private PlayerCharacter character;
  private Item item;

  public CharacterDroppedItemEvent(PlayerCharacter fighter, Item item)
  {
    super(fighter);
    this.item = item;
  }

  public PlayerCharacter getCharacter()
  {
    return character;
  }

  public Item getItem()
  {
    return item;
  }

}
