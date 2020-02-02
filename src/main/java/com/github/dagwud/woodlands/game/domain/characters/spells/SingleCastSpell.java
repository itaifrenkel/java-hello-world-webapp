package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;

public abstract class SingleCastSpell extends Spell
{
  private static final long serialVersionUID = 1L;

  SingleCastSpell(String spellName, Fighter caster)
  {
    super(spellName, caster);
  }

}
