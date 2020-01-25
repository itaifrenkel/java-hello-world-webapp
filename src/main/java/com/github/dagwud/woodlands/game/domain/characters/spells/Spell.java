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

  public abstract boolean shouldCast();
  public abstract void cast();
  public abstract void expire();

  public final String buildSpellDescription()
  {
    return caster.getName() + " ✨ " + spellName;
  }

  protected GameCharacter getCaster()
  {
    return caster;
  }
}
