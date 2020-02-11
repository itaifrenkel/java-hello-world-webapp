package com.github.dagwud.woodlands.game.domain.trinkets.consumable;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.RecoverHitPointsCmd;
import com.github.dagwud.woodlands.game.domain.Fighter;

public abstract class HealingPotion extends ConsumableTrinket
{
  private final int hitPointsToRecover;

  public HealingPotion(String name, int hitPointsToRecover)
  {
    super(name, POTION_ICON);
    this.hitPointsToRecover = hitPointsToRecover;
  }

  @Override
  final void consume(Fighter fighter)
  {
    RecoverHitPointsCmd heal = new RecoverHitPointsCmd(fighter, hitPointsToRecover);
    CommandDelegate.execute(heal);
  }

  @Override
  final String produceConsumptionMessage()
  {
    return "You drain the bottle's murky contents, and after a brief moment of nausea, you feel your aches begin to subside.";
  }

  @Override
  public final String statsSummary(Fighter carrier)
  {
    return "❤" + hitPointsToRecover;
  }
}
