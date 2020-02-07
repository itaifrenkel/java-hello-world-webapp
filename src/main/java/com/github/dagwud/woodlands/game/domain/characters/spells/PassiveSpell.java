package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;

public abstract class PassiveSpell extends Spell
{
  private static final long serialVersionUID = 1L;

  PassiveSpell(String spellName, Fighter caster)
  {
    super(spellName, caster);
  }

  @Override
  public final int getManaCost()
  {
    return 0;
  }
}
