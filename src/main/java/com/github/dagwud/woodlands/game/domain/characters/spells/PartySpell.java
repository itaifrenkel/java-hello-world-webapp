package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

/**
 * Spell that gives benefits to whichever party the caster is part of
 */
public abstract class PartySpell extends Spell
{
  PartySpell(String spellName, GameCharacter caster)
  {
    super(spellName, caster);
  }

  @Override
  GameCharacter getCaster()
  {
    return (GameCharacter) super.getCaster();
  }
}
