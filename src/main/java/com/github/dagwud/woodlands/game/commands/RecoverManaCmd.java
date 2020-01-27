package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.EState;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.characters.spells.PartySpell;

public class RecoverManaCmd extends AbstractCmd
{
  private final Fighter target;
  private final int manaRecovered;

  public RecoverManaCmd(Fighter target, int manaRecovered)
  {
    this.target = target;
    this.manaRecovered = manaRecovered;
  }

  @Override
  public void execute()
  {
    int newMana = Math.min(target.getStats().getMana() + manaRecovered, target.getStats().getMaxMana());
    target.getStats().setMana(newMana);
  }
}
