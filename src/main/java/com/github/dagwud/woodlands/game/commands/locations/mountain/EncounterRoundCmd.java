package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class EncounterRoundCmd extends AbstractCmd
{
  private static final long DELAY_BETWEEN_ROUNDS_MS = 5000;
  private static final String CRITICAL_HIT_ICON = "\uD83C\uDFAF";
  private static final String MISSED_ICON = "\uD83D\uDE48";

  private final int chatId;
  private final Encounter encounter;

  EncounterRoundCmd(int chatId, Encounter encounter)
  {
    this.chatId = chatId;
    this.encounter = encounter;
  }

  @Override
  public void execute()
  {
    String description;
    Weapon leftWeapon = encounter.getHost().getCarrying().getCarriedLeft();

    HitStatus hitResult = rollForHit(encounter.getHost(), leftWeapon, encounter.getEnemy());
    if (hitResult == HitStatus.MISS)
    {
      description = encounter.getHost().getName() + " " + MISSED_ICON + " missed → " + encounter.getEnemy();
    }
    else
    {
      DamageInflicted hostDamageInflicted = rollForDamage(encounter.getHost(), leftWeapon, hitResult == HitStatus.CRITICAL_HIT);
      description = encounter.getHost().getName() + " " +
              hostDamageInflicted.getInflictedWith().getIcon() +
              hostDamageInflicted.getBaseDamage() + "+" + hostDamageInflicted.getBonusDamage() +
              " → " + encounter.getEnemy().name
              + (hitResult == HitStatus.CRITICAL_HIT ? " (" + CRITICAL_HIT_ICON + ")" : "");
    }
    SendMessageCmd status = new SendMessageCmd(chatId, description);
    CommandDelegate.execute(status);

    scheduleNextRound();
  }

  private HitStatus rollForHit(GameCharacter attacker, Weapon attackWith, Creature defender)
  {
    DiceRollCmd naturalRoll = new DiceRollCmd(1, 20);
    CommandDelegate.execute(naturalRoll);
    if (naturalRoll.getTotal() == 1)
    {
      return HitStatus.MISS;
    }
    if (naturalRoll.getTotal() == 20)
    {
      return HitStatus.CRITICAL_HIT;
    }

    int modifier = attackWith.ranged ? attacker.getStats().getAgility() : attacker.getStats().getStrength();
    int weaponBoost = attacker.getStats().getWeaponBonusHit(attackWith);

    int defenderDefenceLevel = defender.level; // todo - not at all a good enough formula.
    if (naturalRoll.getTotal() + modifier + weaponBoost >= defenderDefenceLevel)
    {
      return HitStatus.HIT;
    }
    return HitStatus.MISS;
  }

  private DamageInflicted rollForDamage(GameCharacter attacker, Weapon weaponUsed, boolean criticalHit)
  {
    DiceRollCmd rollDamage = new DiceRollCmd(weaponUsed.damage.diceCount, weaponUsed.damage.diceFaces);
    CommandDelegate.execute(rollDamage);
    int damageDone = rollDamage.getTotal();

    int criticalHitDamage = 0;
    if (criticalHit)
    {
      DiceRollCmd criticalHitRollDamage = new DiceRollCmd(weaponUsed.damage.diceCount, weaponUsed.damage.diceFaces);
      CommandDelegate.execute(criticalHitRollDamage);
      criticalHitDamage = criticalHitRollDamage.getTotal();
    }

    int bonusDamage = attacker.getStats().getWeaponBonusDamage(weaponUsed);

    return new DamageInflicted(weaponUsed, damageDone, criticalHitDamage + bonusDamage);
  }

  private void scheduleNextRound()
  {
    RunLaterCmd nextEncounter = new RunLaterCmd(DELAY_BETWEEN_ROUNDS_MS, new EncounterRoundCmd(chatId, encounter));
    CommandDelegate.execute(nextEncounter);
  }
}
