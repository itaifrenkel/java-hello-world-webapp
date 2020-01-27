package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.BeastMode;

public class Brawler extends PlayerCharacter
{
  Brawler(Player playedBy)
  {
    super(playedBy, ECharacterClass.BRAWLER);
    getSpellAbilities().register(new BeastMode(this));
  }
}
