package com.github.dagwud.woodlands.game.domain;

import com.github.dagwud.woodlands.game.Icons;
import com.github.dagwud.woodlands.game.commands.battle.EHitStatus;
import com.github.dagwud.woodlands.gson.game.Weapon;

import java.io.Serializable;

public class DamageInflicted implements Serializable
{
  private static final long serialVersionUID = 1L;

  private final Weapon inflictedWith;
  private final int baseDamage;
  private final int bonusDamage;
  private final Fighter attacker;
  private final Fighter defender;
  private EHitStatus hitStatus;
  private boolean killingBlow;

  public DamageInflicted(Fighter attacker, Weapon inflictedWith, EHitStatus hitStatus, int baseDamage, Fighter defender, int bonusDamage)
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

  public EHitStatus getHitStatus()
  {
    return hitStatus;
  }

  public int getBonusDamage()
  {
    return bonusDamage;
  }

  public Fighter getAttacker()
  {
    return attacker;
  }

  private Fighter getDefender()
  {
    return defender;
  }

  public String buildDamageDescription()
  {
    if (hitStatus == EHitStatus.DO_NOTHING)
    {
      return attacker.getName() + " " +
          (attacker.isDead() ? Icons.DEAD + "️" : Icons.DO_NOTHING) +
          " did nothing";
    }
    if (hitStatus == EHitStatus.MISS)
    {
      return attacker.getName() + " " + Icons.MISS + " missed → " + defender.getName();
    }
    return attacker.getName() + " " +
            inflictedWith.getIcon() +
            baseDamage + (bonusDamage != 0 ? "+" + bonusDamage : "") +
            " → " + defender.getName()
            + hitIcon(hitStatus)
            + (isKillingBlow() ? Icons.DEAD + "️" : "");
  }

  private String hitIcon(EHitStatus hitStatus)
  {
    if (hitStatus == EHitStatus.CRITICAL_HIT)
    {
      return " (" + Icons.CRITICAL_HIT + ")";
    }
    return "";
  }

  private boolean isKillingBlow()
  {
    return killingBlow;
  }

  public void setKillingBlow(boolean killingBlow)
  {
    this.killingBlow = killingBlow;
  }
}
