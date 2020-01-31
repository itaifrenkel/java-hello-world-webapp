package com.github.dagwud.woodlands.game.domain.spells;

import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.characters.spells.BattleRoundSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

import java.io.Serializable;
import java.util.*;

public class SpellAbilities implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Collection<BattleRoundSpell> passives;
  private Collection<PartySpell> partySpells;
  private Deque<SingleCastSpell> preparedSpells;

  public SpellAbilities()
  {
    this.passives = new ArrayList<>(1);
    this.partySpells = new ArrayList<>(1);
    preparedSpells = new LinkedList<>();
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

  public void prepare(SingleCastSpell spell)
  {
    preparedSpells.clear();
    preparedSpells.addLast(spell);
  }

  public boolean hasPreparedSpell()
  {
    return !preparedSpells.isEmpty();
  }

  public SingleCastSpell popPrepared()
  {
    return preparedSpells.pop();
  }
}
