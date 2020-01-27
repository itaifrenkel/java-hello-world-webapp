package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

public class CastSpellCmd extends AbstractCmd
{
  private Spell spell;

  public CastSpellCmd(Spell spell)
  {
    this.spell = spell;
  }

  @Override
  public void execute()
  {
    spell.cast();
  }
}
