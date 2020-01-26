package com.github.dagwud.woodlands.game.domain.spells;

import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.characters.spells.BattleRoundSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

import java.util.ArrayList;
import java.util.Collection;

public class SpellAbilities
{
  private Collection<BattleRoundSpell> passives;
  private Collection<PartySpell> partySpells;

  public SpellAbilities()
  {
    this.passives = new ArrayList<>(1);
    this.partySpells = new ArrayList<>(1);
  }

  public Collection<BattleRoundSpell> getPassives()
  {
    return passives;
  }

  public Collection<PartySpell> getPartySpells()
  {
    return partySpells;
  }

  public void register(Spell spell)
  {
    if (spell instanceof BattleRoundSpell)
    {
      passives.add((BattleRoundSpell) spell);
    }
    else if (spell instanceof PartySpell)
    {
      partySpells.add((PartySpell) spell);
    }
    else
    {
      throw new WoodlandsRuntimeException("Unknown spell type: " + spell.getClass());
    }
  }
}
