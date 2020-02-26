package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Icons;
import com.github.dagwud.woodlands.game.commands.RecoverManaCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;

public abstract class ManaPotion extends ConsumableTrinket
{
  private static final long serialVersionUID = 1L;
  private final int manaPointsToRecover;

  ManaPotion(String name, int manaPointsToRecover)
  {
    super(name, POTION_ICON);
    this.manaPointsToRecover = manaPointsToRecover;
  }

  @Override
  final void consume(Fighter fighter)
  {
    RecoverManaCmd mana = new RecoverManaCmd(fighter, manaPointsToRecover);
    CommandDelegate.execute(mana);
  }

  @Override
  final String produceConsumptionMessage()
  {
    return "You drink the sparkling liquid, and your arms begin to tingle with the feeling of mana rushing back through your veins";
  }

  @Override
  public final String statsSummary(Fighter carrier)
  {
    return Icons.MANA + manaPointsToRecover;
  }
}
