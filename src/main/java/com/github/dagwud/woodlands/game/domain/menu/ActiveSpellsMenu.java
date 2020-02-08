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

    System.arraycopy(strings, 0, spells, 0, strings.length);

    Iterator<SingleCastSpell> iterator = knownActiveSpell.iterator();
    for (int i = strings.length; i < strings.length + knownActiveSpell.size(); i++)
    {
      spells[i] = iterator.next().getSpellName();
    }
    return spells;
  }
}
