package com.github.dagwud.woodlands.game.domain.stats;

public class BrawlerInitialStats extends InitialStats
{
  public BrawlerInitialStats()
  {
    shortRestDice = 12;
    initialStrength = 16;
    initialStrengthModifier = 3 + strengthBoost;
    initialAgility = 14;
    initialAgilityModifier = 2 + agilityBoost;
    initialConstitution = 15;
    constitutionBoost = 2;
    initialConstitutionModifier = 2 + constitutionBoost;
    initialIntelligenceUnused = 9;
    initialIntelligenceModifierUnused = 1;
    initialWisdomUnused = 13;
    initialWisdomModifierUnused = 1;
    initialCharismaUnused = 11;
    initialCharismaModifierUnused = 0;
    initialProficiencyBonus = 2;
    initialArmorClass = 14;
    initialInitiative = 2;
    initialSpeedUnused = 30;
    initialHitPoints = 14;
    initialHitDice = "1d12";
    weaponMasteryBonusHit.put("Warrior Club", 5);
    weaponMasteryBonusDamage.put("Warrior Club", 3);
    weaponMasteryBonusHit.put("Hand Axe", 5);
    weaponMasteryBonusDamage.put("Hand Axe", 3);
    weaponMasteryBonusHit.put("Javelin", 5);
    weaponMasteryBonusDamage.put("Javelin", 3);
  }

}
