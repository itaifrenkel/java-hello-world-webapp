package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public abstract class Spell
{
  private final String spellName;
  private final Fighter caster;

  Spell(String spellName, Fighter caster)
  {
    this.spellName = spellName;
    this.caster = caster;
  }

  public abstract void cast();
  public abstract void expire();

  public String buildSpellDescription()
  {
    return caster.getName() + " ✨ " + spellName;
  }

  Fighter getCaster()
  {
    return caster;
  }
}
