package com.github.dagwud.woodlands.game.domain.menu;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.domain.characters.spells.SingleCastSpell;

import java.util.Collection;
import java.util.Iterator;

public abstract class ActiveSpellsMenu extends GameMenu
{
  @Override
  public String[] produceOptions(PlayerState playerState)
  {
    String[] strings = super.produceOptions(playerState);
    Collection<SingleCastSpell> knownActiveSpell = playerState.getActiveCharacter().getSpellAbilities().getKnownActiveSpell();
    String[] spells = new String[strings.length + knownActiveSpell.size()];

    Iterator<SingleCastSpell> iterator = knownActiveSpell.iterator();
    for (int i = 0; i < knownActiveSpell.size(); i++)
    {
      spells[i] = iterator.next().getSpellName();
    }

    System.arraycopy(strings, 0, spells, knownActiveSpell.size(), strings.length);

    return spells;
  }
}
