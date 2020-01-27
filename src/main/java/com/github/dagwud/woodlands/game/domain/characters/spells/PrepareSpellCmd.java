package com.github.dagwud.woodlands.game.domain.characters.spells;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;

public class PrepareSpellCmd extends AbstractCmd
{
  private final Fighter caster;
  private final SingleCastSpell spell;

  public PrepareSpellCmd(Fighter caster, SingleCastSpell spell)
  {
    this.caster = caster;
    this.spell = spell;
  }

  @Override
  public void execute()
  {
    caster.getSpellAbilities().prepare(spell);
  }
}
