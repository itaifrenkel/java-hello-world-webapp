package com.github.dagwud.woodlands.game.domain.stats;

public class TricksterInitialStats extends InitialStats
{
  TricksterInitialStats()
  {
    shortRestDice = 8;
    initialStrength = 11;
    initialStrengthModifier = 0;
    initialAgility = 16;
    initialAgilityModifier = 3;
    initialConstitution = 14;
    initialConstitutionModifier = 2;
    initialIntelligenceUnused = 15;
    initialIntelligenceModifierUnused = 2;
    initialWisdomUnused = 9;
    initialWisdomModifierUnused = 1;
    initialCharismaUnused = 11;
    initialCharismaModifierUnused = 1;
    initialProficiencyBonus = 2;
    initialArmorClass = 14;
    initialInitiative = 3;
    initialSpeedUnused = 30;
    initialHitPoints = 10;
    initialHitDice = "1d8";
    weaponMasteryBonusHit.put("Katana", 5);
    weaponMasteryBonusDamage.put("Katana", 3);
    weaponMasteryBonusHit.put("Dart Gun", 5);
    weaponMasteryBonusDamage.put("Dart Gun", 3);
    weaponMasteryBonusHit.put("Dagger", 5);
    weaponMasteryBonusDamage.put("Dagger", 3);
  }
}
