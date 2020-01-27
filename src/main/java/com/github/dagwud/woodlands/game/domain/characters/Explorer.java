package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.SpiritOfAdventure;

public class Explorer extends PlayerCharacter
{
  Explorer(Player playedBy)
  {
    super(playedBy, ECharacterClass.EXPLORER);
    getSpellAbilities().register(new SpiritOfAdventure(this));
  }
}
