package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

/**
 * Spell that applies for the length of a single round of battle
 */
public abstract class BattleRoundSpell extends Spell
{
  BattleRoundSpell(String spellName, Fighter caster)
  {
    super(spellName, caster);
  }

  public abstract boolean shouldCast();
}
