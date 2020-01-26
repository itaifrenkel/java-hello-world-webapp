package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.GameCharacter;

public abstract class Spell
{
  private final String spellName;
  private final GameCharacter caster;

  Spell(String spellName, GameCharacter caster)
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

  GameCharacter getCaster()
  {
    return caster;
  }
}
