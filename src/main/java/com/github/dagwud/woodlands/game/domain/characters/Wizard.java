package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.AuraOfProtection;

class Wizard extends PlayerCharacter
{
  Wizard(Player playedBy)
  {
    super(playedBy, ECharacterClass.WIZARD);
    getSpellAbilities().register(new AuraOfProtection(this));
  }
}
