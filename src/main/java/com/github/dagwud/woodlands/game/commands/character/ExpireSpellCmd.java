package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

public class ExpireSpellCmd extends AbstractCmd
{
  private Spell spell;

  ExpireSpellCmd(Spell spell)
  {
    this.spell = spell;
  }

  @Override
  public void execute()
  {
    if (spell.isCast())
    {
      spell.expire();
      spell.setCast(false);
    }
  }
}
