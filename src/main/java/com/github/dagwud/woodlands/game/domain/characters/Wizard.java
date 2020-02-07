package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.PlayerCharacter;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.characters.spells.AuraOfProtection;
import com.github.dagwud.woodlands.game.domain.characters.spells.FlameAttack;

class Wizard extends PlayerCharacter
{
  private static final long serialVersionUID = 1L;

  Wizard(Player playedBy)
  {
    super(playedBy, ECharacterClass.WIZARD);
    getSpellAbilities().register(new AuraOfProtection(this)); // passive
    getSpellAbilities().register(new FlameAttack(this)); // active
  }
}
