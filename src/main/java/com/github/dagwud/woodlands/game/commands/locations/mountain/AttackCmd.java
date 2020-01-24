package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.Fighter;
import com.github.dagwud.woodlands.game.domain.stats.Stat;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class AttackCmd extends AbstractCmd
{
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
      damageInflicted = new DamageInflicted(attacker, weaponUsed, EHitStatus.DO_NOTHING, 0, defender, 0);
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
    int drunkennessPenalty = determineDrunkennessModifier(attacker);

    DiceRollCmd naturalRoll = new DiceRollCmd(1, 20);
    CommandDelegate.execute(naturalRoll);
    if (naturalRoll.getTotal() <= 1 + drunkennessPenalty)
    {
      return EHitStatus.MISS;
    }
    if (naturalRoll.getTotal() == 20)
    {
      return EHitStatus.CRITICAL_HIT;
    }

    Stat modifier = attackWith.ranged ? attacker.getStats().getAgility() : attacker.getStats().getStrength();
    int weaponBoost = attacker.getStats().getWeaponBonusHit(attackWith);

    int defenderDefenceRating = defender.getStats().getDefenceRating();
    if (naturalRoll.getTotal() + modifier.total() + weaponBoost - drunkennessPenalty >= defenderDefenceRating)
    {
      return EHitStatus.HIT;
    }
    return EHitStatus.MISS;
  }

  private void rollForDamage(EHitStatus hitStatus)
  {
    DiceRollCmd rollDamage = new DiceRollCmd(weaponUsed.damage.diceCount, weaponUsed.damage.diceFaces);
    CommandDelegate.execute(rollDamage);
    int damageDone = rollDamage.getTotal();

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
      drunkStrengthDamage = determineDrunkennessModifier(attacker);
    }

    damageInflicted = new DamageInflicted(attacker, weaponUsed, hitStatus,
            damageDone, defender, criticalHitDamage + bonusDamage + drunkStrengthDamage);
  }

  private int determineDrunkennessModifier(Fighter attacker)
  {
    return Math.min(attacker.getStats().getDrunkeness() / 2, 4);
  }

  DamageInflicted getDamageInflicted()
  {
    return damageInflicted;
  }
}
