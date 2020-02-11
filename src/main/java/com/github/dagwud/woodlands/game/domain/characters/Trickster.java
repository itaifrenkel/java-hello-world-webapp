package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.characters.spells.Distraction;
import com.github.dagwud.woodlands.game.domain.characters.spells.SneakAttack;

public class Trickster extends PlayerCharacter
{
  private static final long serialVersionUID = 1L;

  Trickster(Player playedBy)
  {
    super(playedBy, ECharacterClass.TRICKSTER);

    getSpellAbilities().register(new Distraction(this)); // active
    getSpellAbilities().register(new SneakAttack(this)); // active
  }

  @Override
  public int determineMaxAllowedItems()
  {
    // Loot bag
    return super.determineMaxAllowedItems() + 3;
  }

}
