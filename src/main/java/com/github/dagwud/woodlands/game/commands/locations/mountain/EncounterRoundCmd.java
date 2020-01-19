package com.github.dagwud.woodlands.game.commands.locations.mountain;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.battle.DealDamageCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.DiceRollCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.DamageInflicted;
import com.github.dagwud.woodlands.game.domain.Encounter;
import com.github.dagwud.woodlands.game.domain.GameCharacter;
import com.github.dagwud.woodlands.game.domain.stats.Stats;
import com.github.dagwud.woodlands.gson.game.Creature;
import com.github.dagwud.woodlands.gson.game.Weapon;

public class EncounterRoundCmd extends AbstractCmd
{
  private static final String CRITICAL_HIT_ICON = "\uD83C\uDFAF";
  private static final String MISSED_ICON = "\uD83D\uDE48";

  private final int chatId;
  private final Encounter encounter;
  private final int delayBetweenRoundsMS;

  EncounterRoundCmd(int chatId, Encounter encounter, int delayBetweenRoundsMS)
  {
    this.chatId = chatId;
    this.encounter = encounter;
    this.delayBetweenRoundsMS = delayBetweenRoundsMS;
  }

  @Override
  public void execute()
  {
    if (encounter.isEnded())
    {
      return;
    }

    doAttack(encounter.getHost(), encounter.getHost().getCarrying().getCarriedLeft(), encounter.getEnemy());
    doAttack(encounter.getHost(), encounter.getHost().getCarrying().getCarriedRight(), encounter.getEnemy());

    scheduleNextRound();
  }

  private void doAttack(GameCharacter attacker, Weapon attackWith, Creature defender)
  {
    String description;
    if (attackWith == null)
    {
      description = attacker.getName() + " does nothing";
    }
    else
    {
      HitStatus hitResult = rollForHit(attacker, attackWith, defender);
      if (hitResult == HitStatus.MISS)
      {
        description = attacker.getName() + " " + MISSED_ICON + " missed → " + defender.name;
      }
      else
      {
        DamageInflicted hostDamageInflicted = rollForDamage(attacker, attackWith, hitResult == HitStatus.CRITICAL_HIT);
        DealDamageCmd cmd = new DealDamageCmd(hostDamageInflicted, defender);
        CommandDelegate.execute(cmd);

        description = attacker.getName() + " " +
                hostDamageInflicted.getInflictedWith().getIcon() +
                hostDamageInflicted.getBaseDamage() +
                (hostDamageInflicted.getBonusDamage() != 0 ? "+" + hostDamageInflicted.getBonusDamage() : "") +
                " → " + defender.name
                + (hitResult == HitStatus.CRITICAL_HIT ? " (" + CRITICAL_HIT_ICON + ")" : "");
      }
    }

    description += "\n\n" + buildBattleStatsSummary();

    SendMessageCmd status = new SendMessageCmd(chatId, description);
    CommandDelegate.execute(status);
  }

  private String buildBattleStatsSummary()
  {
    return "Stats:\n" +
            "-------------" +
            "\n" + buildCharacterSummaryLine(encounter.getHost().getName(), encounter.getHost().getStats()) +
            "\n" + buildCharacterSummaryLine(encounter.getEnemy().name, encounter.getEnemy().getStats());
  }


  private String buildCharacterSummaryLine(String characterName, Stats stats)
  {
    return "• " + characterName + ": \uD83D\uDC9A"
            + stats.getHitPoints() + "/" + stats.getMaxHitPoints()
            + ", " + stats.getMana() + "/" + stats.getMaxMana();
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

    int defenderDefenceRating = determineDefenceRating(defender);
    if (naturalRoll.getTotal() + modifier + weaponBoost >= defenderDefenceRating)
    {
      return HitStatus.HIT;
    }
    return HitStatus.MISS;
  }

  private int determineDefenceRating(Creature defender)
  {
    if (defender.difficulty <= 3)
    {
      return 13;
    }
    if (defender.difficulty == 4)
    {
      return 14;
    }
    if (defender.difficulty <= 7)
    {
      return 15;
    }
    if (defender.difficulty <= 9)
    {
      return 16;
    }
    if (defender.difficulty <= 12)
    {
      return 17;
    }
    return defender.difficulty + 5;
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
    EncounterRoundCmd nextRoundCmd = new EncounterRoundCmd(chatId, encounter, delayBetweenRoundsMS);
    RunLaterCmd nextEncounter = new RunLaterCmd(delayBetweenRoundsMS, nextRoundCmd);
    CommandDelegate.execute(nextEncounter);
  }
}
