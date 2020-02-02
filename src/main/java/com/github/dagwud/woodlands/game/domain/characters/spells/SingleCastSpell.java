package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;

public abstract class SingleCastSpell extends Spell
{
  private static final long serialVersionUID = 1L;

  private DamageInflicted damageInflicted;

  SingleCastSpell(String spellName, Fighter caster)
  {
    super(spellName, caster);
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
