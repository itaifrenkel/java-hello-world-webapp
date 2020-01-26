package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.GameCharacter;

/**
 * Spell that applies for the length of a single round of battle
 */
public abstract class BattleRoundSpell extends Spell
{
  BattleRoundSpell(String spellName, GameCharacter caster)
  {
    super(spellName, caster);
  }

  public abstract boolean shouldCast();
}
