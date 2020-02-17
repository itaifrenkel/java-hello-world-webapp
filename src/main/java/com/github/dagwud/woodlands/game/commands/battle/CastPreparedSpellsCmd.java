package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.CastSpellCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;

import java.util.ArrayList;
import java.util.List;

public class CastPreparedSpellsCmd extends AbstractCmd
{
  private final List<Fighter> fighters;
  private final Encounter encounter;
  private List<SingleCastSpell> spellsCast;

  public CastPreparedSpellsCmd(List<Fighter> fighters, Encounter encounter)
  {
    this.fighters = fighters;
    this.encounter = encounter;
  }

  @Override
  public void execute()
  {
    spellsCast = new ArrayList<>(2);
    for (Fighter caster : fighters)
    {
      while (caster.getSpellAbilities().hasPreparedSpell())
      {
        SingleCastSpell spell = caster.getSpellAbilities().popPrepared();
        CastSpellCmd cast = new CastSpellCmd(spell);
        CommandDelegate.execute(cast);
        spellsCast.add(spell);
        encounter.markNotFarmed();
      }
    }
  }

  public List<SingleCastSpell> getSpellsCast()
  {
    return spellsCast;
  }
}
