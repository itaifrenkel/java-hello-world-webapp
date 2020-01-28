package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.domain.PlayerCharacter;

/**
 * Spell that gives benefits to whichever party the caster is part of
 */
public abstract class PartySpell extends PassiveSpell
{
  PartySpell(String spellName, PlayerCharacter caster)
  {
    super(spellName, caster);
  }

  @Override
  public PlayerCharacter getCaster()
  {
    return (PlayerCharacter) super.getCaster();
  }
}
