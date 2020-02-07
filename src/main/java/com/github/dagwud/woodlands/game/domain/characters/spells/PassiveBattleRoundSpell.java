package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;

/**
 * Spell that applies for the length of a single round of battle
 */
public abstract class PassiveBattleRoundSpell extends PassiveSpell
{
  private static final long serialVersionUID = 1L;

  PassiveBattleRoundSpell(String spellName, Fighter caster)
  {
    super(spellName, caster);
  }

  public abstract boolean shouldCast();
}
