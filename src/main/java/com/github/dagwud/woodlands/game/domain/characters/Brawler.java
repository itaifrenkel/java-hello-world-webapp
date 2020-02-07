package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.BeastMode;
import com.github.dagwud.woodlands.game.domain.characters.spells.KnuckleDuster;

public class Brawler extends PlayerCharacter
{
  private static final long serialVersionUID = 1L;

  Brawler(Player playedBy)
  {
    super(playedBy, ECharacterClass.BRAWLER);
    getSpellAbilities().register(new BeastMode(this)); // passive
    getSpellAbilities().register(new KnuckleDuster(this)); // active
  }
}
