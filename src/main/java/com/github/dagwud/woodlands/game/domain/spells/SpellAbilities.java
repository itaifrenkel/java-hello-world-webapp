package com.github.dagwud.woodlands.game.domain.spells;

import com.github.dagwud.woodlands.game.domain.WoodlandsRuntimeException;
import com.github.dagwud.woodlands.game.domain.characters.spells.PassiveBattleRoundSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.PassivePartySpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;
import com.github.dagwud.woodlands.game.domain.characters.spells.Spell;

import java.io.Serializable;
import java.util.*;

public class SpellAbilities implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Collection<PassiveBattleRoundSpell> passives;
  private Collection<PassivePartySpell> passivePartySpells;
  private Deque<SingleCastSpell> preparedSpells;

  public SpellAbilities()
  {
    this.passives = new ArrayList<>(1);
    this.passivePartySpells = new ArrayList<>(1);
    preparedSpells = new LinkedList<>();
  }

  public Collection<PassiveBattleRoundSpell> getPassives()
  {
    return passives;
  }

  public Collection<PassivePartySpell> getPassivePartySpells()
  {
    return passivePartySpells;
  }

  public void register(Spell spell)
  {
    if (spell instanceof PassiveBattleRoundSpell)
    {
      passives.add((PassiveBattleRoundSpell) spell);
    }
    else if (spell instanceof PassivePartySpell)
    {
      passivePartySpells.add((PassivePartySpell) spell);
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
