package com.github.dagwud.woodlands.game.domain.characters;

import com.github.dagwud.woodlands.game.domain.ECharacterClass;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.Player;

class Wizard extends GameCharacter
{
  Wizard(Player playedBy)
  {
    super(playedBy, ECharacterClass.WIZARD);
  }
}
