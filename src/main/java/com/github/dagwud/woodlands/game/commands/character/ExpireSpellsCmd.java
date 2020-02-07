package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

import java.util.Collection;

public class ExpireSpellsCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private Collection<? extends Spell> spells;

  public ExpireSpellsCmd(Collection<? extends Spell> spells)
  {
    this.spells = spells;
  }

  @Override
  public void execute()
  {
    for (Spell spell : spells)
    {
      ExpireSpellCmd expire = new ExpireSpellCmd(spell);
      CommandDelegate.execute(expire);
    }
  }
}
