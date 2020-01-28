package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;

abstract class PassiveSpell extends Spell
{
  PassiveSpell(String spellName, Fighter caster)
  {
    super(spellName, caster);
  }

  @Override
  public int getManaCost()
  {
    return 0;
  }
}
