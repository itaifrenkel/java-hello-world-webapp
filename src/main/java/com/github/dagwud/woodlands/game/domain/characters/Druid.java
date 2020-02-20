package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.ArcaneInspiration;
import com.github.dagwud.woodlands.game.domain.characters.spells.FogOfConfusion;
import com.github.dagwud.woodlands.game.domain.characters.spells.HerbalRemedy;

class Druid extends PlayerCharacter
{
  private static final long serialVersionUID = 1L;

  Druid(Player playedBy)
  {
    super(playedBy, ECharacterClass.DRUID);
    getSpellAbilities().register(new ArcaneInspiration(this)); // passive
    getSpellAbilities().register(new FogOfConfusion(this)); // active
    getSpellAbilities().register(new HerbalRemedy(this)); // active
  }
}
