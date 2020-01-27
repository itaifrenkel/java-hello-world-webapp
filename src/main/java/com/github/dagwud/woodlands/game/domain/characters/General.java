package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.AirOfAuthority;

public class General extends PlayerCharacter
{
  General(Player playedBy)
  {
    super(playedBy, ECharacterClass.GENERAL);
    getSpellAbilities().register(new AirOfAuthority(this));
  }
}
