package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.IFighter;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class AttackCmd extends AbstractCmd
{
  private final IFighter attacker;
  private final IFighter defender;
  private final Weapon weaponUsed;
  private DamageInflicted damageInflicted;

  AttackCmd(IFighter attacker, Weapon weaponUsed, IFighter defender)
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

  private EHitStatus rollForHit(IFighter attacker, Weapon attackWith, IFighter defender)
  {
    DiceRollCmd naturalRoll = new DiceRollCmd(1, 20);
    CommandDelegate.execute(naturalRoll);
    if (naturalRoll.getTotal() == 1)
    {
      return EHitStatus.MISS;
    }
    if (naturalRoll.getTotal() == 20)
    {
      return EHitStatus.CRITICAL_HIT;
    }

    int modifier = attackWith.ranged ? attacker.getStats().getAgility() : attacker.getStats().getStrength();
    int weaponBoost = attacker.getStats().getWeaponBonusHit(attackWith);
    int drunkennessPenalty = determineDrunkennessModifier(attacker);
    int defenderDefenceRating = defender.getStats().getDefenceRating();
    if (naturalRoll.getTotal() + modifier + weaponBoost - drunkennessPenalty >= defenderDefenceRating)
    {
      return EHitStatus.HIT;
    }
    return EHitStatus.MISS;
  }

  private int determineDrunkennessModifier(IFighter attacker)
  {
    return Math.min(attacker.getStats().getDrunkeness(), 5);
  }

  DamageInflicted getDamageInflicted()
  {
    return damageInflicted;
  }
}
