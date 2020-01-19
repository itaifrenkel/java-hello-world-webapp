package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.gson.game.Creature;

public class DealDamageCmd extends AbstractCmd
{
  private final DamageInflicted damageInflicted;
  private final Creature inflictedOn;

  public DealDamageCmd(DamageInflicted damageInflicted, Creature inflictedOn)
  {
    this.damageInflicted = damageInflicted;
    this.inflictedOn = inflictedOn;
  }

  @Override
  public void execute()
  {
    int totalDamageInflicted = damageInflicted.getBaseDamage() + damageInflicted.getBonusDamage();
    int newHP = inflictedOn.getStats().getHitPoints() - totalDamageInflicted;
    inflictedOn.getStats().setHitPoints(newHP);

    if (inflictedOn.getStats().getHitPoints() <= 0)
    {
      if (inflictedOn.getStats().getHitPoints() < -inflictedOn.getStats().getMaxHitPoints())
      {
        // instant death:
        DeathCmd cmd = new DeathCmd(inflictedOn);
        CommandDelegate.execute(cmd);
      }
      else
      {
        // unconscious:
        KnockUnconsciousCmd cmd = new KnockUnconsciousCmd(inflictedOn);
        CommandDelegate.execute(cmd);
      }
      inflictedOn.getStats().setHitPoints(0);
    }
  }
}
