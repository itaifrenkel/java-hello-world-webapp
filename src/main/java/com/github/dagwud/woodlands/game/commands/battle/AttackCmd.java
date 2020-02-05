package com.github.dagwud.woodlands.game.commands.battle;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.stats.Stat;
import com.github.dagwud.woodlands.gson.game.Shield;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class AttackCmd extends AbstractCmd
{
  private static final long serialVersionUID = 1L;

  private final Fighter attacker;
  private final Fighter defender;
  private final Weapon weaponUsed;
  private DamageInflicted damageInflicted;

  AttackCmd(Fighter attacker, Weapon weaponUsed, Fighter defender)
  {
    this.attacker = attacker;
    this.weaponUsed = weaponUsed;
    this.defender = defender;
  }

  @Override
  public void execute()
  {
    if (weaponUsed == null)
    {
      damageInflicted = new DamageInflicted(attacker, null, EHitStatus.DO_NOTHING, 0, defender, 0);
      return;
    }

    EHitStatus hitStatus = rollForHit(attacker, weaponUsed, defender);
    if (hitStatus == EHitStatus.MISS)
    {
      damageInflicted = new DamageInflicted(attacker, weaponUsed, EHitStatus.MISS, 0, defender, 0);
    }
    else
    {
      rollForDamage(hitStatus);
    }
  }

  private EHitStatus rollForHit(Fighter attacker, Weapon attackWith, Fighter defender)
  {
    DiceRollCmd naturalRoll = new DiceRollCmd(1, 20);
    CommandDelegate.execute(naturalRoll);

    if (naturalRoll.getTotal() + attacker.getStats().determineHitChanceBoost() <= 1)
    {
System.out.println(attacker.getName() + " natural miss - " + naturalRoll.getTotal() + " + bonus " + attacker.getStats().determineHitChanceBoost());
      return EHitStatus.MISS;
    }
    if (naturalRoll.getTotal() >= 20 - attacker.getStats().getCriticalStrikeChanceBonus())
    {
      return EHitStatus.CRITICAL_HIT;
    }

    Stat modifier = attackWith.ranged ? attacker.getStats().getAgility() : attacker.getStats().getStrength();
    int weaponBoost = attacker.getStats().getWeaponBonusHit(attackWith);

    int defenderDefenceRating = defender.getStats().getBaseDefenceRating() + defender.getStats().getDefenceRatingBoost();
    defenderDefenceRating += countShieldsDefence(defender);

    if (naturalRoll.getTotal() + modifier.total() + weaponBoost + attacker.getStats().determineHitChanceBoost() >= defenderDefenceRating)
    {
      return EHitStatus.HIT;
    }

System.out.println(attacker.getName() + " miss: " + naturalRoll.getTotal() + "+" + modifier.total() + "+" + weaponBoost + "+" + attacker.getStats().determineHitChanceBoost() + " >= " + defenderDefenceRating);
    return EHitStatus.MISS;
  }

  private int countShieldsDefence(Fighter defender)
  {
    int shieldStrength = 0;
    if (defender.getCarrying().getCarriedLeft() instanceof Shield)
    {
      shieldStrength += ((Shield) defender.getCarrying().getCarriedLeft()).strength;
    }
    if (defender.getCarrying().getCarriedRight() instanceof Shield)
    {
      shieldStrength += ((Shield) defender.getCarrying().getCarriedRight()).strength;
    }
    return shieldStrength;
  }

  private void rollForDamage(EHitStatus hitStatus)
  {
    DiceRollCmd rollDamage = new DiceRollCmd(weaponUsed.damage.diceCount, weaponUsed.damage.diceFaces);
    CommandDelegate.execute(rollDamage);
    int baseDamage = rollDamage.getTotal();

    int criticalHitDamage = 0;
    if (hitStatus == EHitStatus.CRITICAL_HIT)
    {
      DiceRollCmd criticalHitRollDamage = new DiceRollCmd(weaponUsed.damage.diceCount, weaponUsed.damage.diceFaces);
      CommandDelegate.execute(criticalHitRollDamage);
      criticalHitDamage = criticalHitRollDamage.getTotal();
    }

    int bonusDamage = attacker.getStats().getWeaponBonusDamage(weaponUsed);
    int drunkStrengthDamage = 0;
    if (!weaponUsed.ranged)
    {
      // Drunken strength:
      drunkStrengthDamage = attacker.getStats().determineDrunkenStrength();
    }

    double damageMultiplier = attacker.getStats().getDamageMultiplier();
    if (damageMultiplier != 1d)
    {
      baseDamage = (int) Math.floor(damageMultiplier * baseDamage);
      bonusDamage = (int) Math.floor(damageMultiplier * bonusDamage);
    }

    damageInflicted = new DamageInflicted(attacker, weaponUsed, hitStatus,
            baseDamage, defender, criticalHitDamage + bonusDamage + drunkStrengthDamage);
  }

  DamageInflicted getDamageInflicted()
  {
    return damageInflicted;
  }

  @Override
  public String toString()
  {
    return "AttackCmd{" +
            "attacker=" + attacker +
            ", defender=" + defender +
            '}';
  }
}
