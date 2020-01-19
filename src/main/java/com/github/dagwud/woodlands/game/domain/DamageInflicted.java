package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.commands.locations.mountain.EHitStatus;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class DamageInflicted
{
  private static final String MISSED_ICON = "\uD83D\uDE48";
  private static final String CRITICAL_HIT_ICON = "\uD83C\uDFAF";

  private final Weapon inflictedWith;
  private final int baseDamage;
  private final int bonusDamage;
  private final GameCharacter attacker;
  private final Creature defender;
  private EHitStatus hitStatus;

  public DamageInflicted(GameCharacter attacker, Weapon inflictedWith, EHitStatus hitStatus, int baseDamage, Creature defender, int bonusDamage)
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

  private GameCharacter getAttacker()
  {
    return attacker;
  }

  private Creature getDefender()
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
      return attacker.getName() + " " + MISSED_ICON + " missed → " + defender.name;
    }
    return attacker.getName() + " " +
            inflictedWith.getIcon() +
            baseDamage + (bonusDamage != 0 ? "+" + bonusDamage : "") +
            " → " + defender.name
            + (hitStatus == EHitStatus.CRITICAL_HIT ? " (" + CRITICAL_HIT_ICON + ")" : "");
  }
}
