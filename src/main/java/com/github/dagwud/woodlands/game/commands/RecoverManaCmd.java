package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;

public class RecoverManaCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Fighter target;
  private int manaRecovered;

  public RecoverManaCmd(Fighter target, int manaRecovered)
  {
    this.target = target;
    this.manaRecovered = manaRecovered;
  }

  @Override
  public void execute()
  {
    int newMana = Math.min(target.getStats().getMana() + manaRecovered, target.getStats().getMaxMana().total());
    this.manaRecovered = newMana - target.getStats().getMana();
    target.getStats().setMana(newMana);
  }

  public int getManaRecovered()
  {
    return manaRecovered;
  }
}
