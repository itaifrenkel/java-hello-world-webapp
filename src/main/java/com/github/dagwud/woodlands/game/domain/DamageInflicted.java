package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.commands.locations.mountain.EHitStatus;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class DamageInflicted
{
  private static final String MISSED_ICON = "\uD83D\uDE48";
  private static final String CRITICAL_HIT_ICON = "\uD83C\uDFAF";

  private final Weapon inflictedWith;
  private final int baseDamage;
  private final int bonusDamage;
  private final IFighter attacker;
  private final IFighter defender;
  private EHitStatus hitStatus;

  public DamageInflicted(IFighter attacker, Weapon inflictedWith, EHitStatus hitStatus, int baseDamage, IFighter defender, int bonusDamage)
  {
    this.attacker = attacker;
    this.inflictedWith = inflictedWith;
    this.baseDamage = baseDamage;
    this.bonusDamage = bonusDamage;
    this.defender = defender;
    this.hitStatus = hitStatus;
  }

  public Weapon getInflictedWith()
  {
    return inflictedWith;
  }

  public int getBaseDamage()
  {
    return baseDamage;
  }

  public int getBonusDamage()
  {
    return bonusDamage;
  }

  private IFighter getAttacker()
  {
    return attacker;
  }

  private IFighter getDefender()
  {
    return defender;
  }

  public String buildDamageDescription()
  {
    if (hitStatus == EHitStatus.DO_NOTHING)
    {
      return attacker.getName() + " does nothing";
    }
    if (hitStatus == EHitStatus.MISS)
    {
      return attacker.getName() + " " + MISSED_ICON + " missed → " + defender.getName();
    }
    return attacker.getName() + " " +
            inflictedWith.getIcon() +
            baseDamage + (bonusDamage != 0 ? "+" + bonusDamage : "") +
            " → " + defender.getName()
            + (hitStatus == EHitStatus.CRITICAL_HIT ? " (" + CRITICAL_HIT_ICON + ")" : "");
  }
}
