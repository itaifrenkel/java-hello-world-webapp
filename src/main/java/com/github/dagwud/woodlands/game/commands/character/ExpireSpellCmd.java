package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

public class ExpireSpellCmd extends AbstractCmd
{
  private Spell spell;

  public ExpireSpellCmd(Spell spell)
  {
    this.spell = spell;
  }

  @Override
  public void execute()
  {
    if (!spell.isCast())
    {
      throw new WoodlandsRuntimeException(spell.getSpellName() + " has not been cast");
    }
    spell.expire();
    spell.setCast(false);
  }
}
