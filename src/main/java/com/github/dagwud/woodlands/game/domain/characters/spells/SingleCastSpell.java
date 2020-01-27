package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;

public abstract class SingleCastSpell extends Spell
{
  SingleCastSpell(String spellName, Fighter caster)
  {
    super(spellName, caster);
  }
}
