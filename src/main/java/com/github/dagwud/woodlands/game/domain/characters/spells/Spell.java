package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;

import java.io.Serializable;

public abstract class Spell implements Serializable
{
  private final String spellName;
  private final Fighter caster;
  private boolean isCast;

  Spell(String spellName, Fighter caster)
  {
    this.spellName = spellName;
    this.caster = caster;
  }

  public abstract void cast();

  public abstract void expire();

  public String buildSpellDescription()
  {
    return caster.getName() + " ✨" + spellName;
  }

  public Fighter getCaster()
  {
    return caster;
  }

  public String getSpellName()
  {
    return spellName;
  }

  public abstract int getManaCost();

  public boolean isCast()
  {
    return isCast;
  }

  public void setCast(boolean cast)
  {
    isCast = cast;
  }
}
