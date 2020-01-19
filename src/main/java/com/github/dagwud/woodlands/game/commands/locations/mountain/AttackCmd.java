package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class AttackCmd extends AbstractCmd
{
  private final GameCharacter attacker;
  private final Creature defender;
  private final Weapon weaponUsed;
  private DamageInflicted damageInflicted;

  AttackCmd(GameCharacter attacker, Weapon weaponUsed, Creature defender)
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

    damageInflicted = new DamageInflicted(attacker, weaponUsed, hitStatus,
            damageDone, defender, criticalHitDamage + bonusDamage);
  }

  private EHitStatus rollForHit(GameCharacter attacker, Weapon attackWith, Creature defender)
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
    int defenderDefenceRating = defender.getStats().getDefenceRating();
    if (naturalRoll.getTotal() + modifier + weaponBoost >= defenderDefenceRating)
    {
      return EHitStatus.HIT;
    }
    return EHitStatus.MISS;
  }

  DamageInflicted getDamageInflicted()
  {
    return damageInflicted;
  }
}
