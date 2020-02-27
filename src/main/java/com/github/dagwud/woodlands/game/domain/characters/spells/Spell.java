package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.Icons;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.Fighter;

import java.io.Serializable;

public abstract class Spell implements Serializable
{
  private static final long serialVersionUID = 1L;

  private final String spellName;
  private final Fighter caster;
  private boolean isCast;
  private DamageInflicted damageInflicted;

  Spell(String spellName, Fighter caster)
  {
    this.spellName = spellName;
    this.caster = caster;
  }

  public abstract boolean cast();

  public abstract void expire();

  public String buildSpellDescription()
  {
    return Icons.MANA + spellName;
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

  public DamageInflicted getDamageInflicted()
  {
    return damageInflicted;
  }

  public void setDamageInflicted(DamageInflicted damageInflicted)
  {
    this.damageInflicted = damageInflicted;
  }

}
