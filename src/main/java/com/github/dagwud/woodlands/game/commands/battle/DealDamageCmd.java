package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.character.ReduceHitPointsCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.IFighter;

public class DealDamageCmd extends AbstractCmd
{
  private final DamageInflicted damageInflicted;
  private final IFighter inflictedOn;

  public DealDamageCmd(DamageInflicted damageInflicted, IFighter inflictedOn)
  {
    this.damageInflicted = damageInflicted;
    this.inflictedOn = inflictedOn;
  }

  @Override
  public void execute()
  {
    int totalDamageInflicted = damageInflicted.getBaseDamage() + damageInflicted.getBonusDamage();
    ReduceHitPointsCmd cmd = new ReduceHitPointsCmd(inflictedOn, totalDamageInflicted);
    CommandDelegate.execute(cmd);
  }
}
