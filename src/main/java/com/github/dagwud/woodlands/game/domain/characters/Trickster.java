package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

public class Trickster extends PlayerCharacter
{
  private static final long serialVersionUID = 1L;

  Trickster(Player playedBy)
  {
    super(playedBy, ECharacterClass.TRICKSTER);
  }

  @Override
  public int determineMaxAllowedItems()
  {
    // Loot bag
    return super.determineMaxAllowedItems() + 2 * getStats().getLevel();
  }

}
