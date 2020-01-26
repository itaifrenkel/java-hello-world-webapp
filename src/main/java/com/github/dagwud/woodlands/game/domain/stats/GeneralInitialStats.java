package com.github.dagwud.woodlands.game.domain.stats;

public class GeneralInitialStats extends InitialStats
{
  public GeneralInitialStats()
  {
    shortRestDice = 8;
    initialStrength = 16;
    strengthBoost = 3;
    initialStrengthModifier = 3 + strengthBoost;
    initialAgility = 14;
    initialAgilityModifier = 2 + agilityBoost;
    initialConstitution = 15;
    initialConstitutionModifier = 2 + constitutionBoost;
    initialIntelligenceUnused = 11;
    initialIntelligenceModifierUnused = 0;
    initialWisdomUnused = 13;
    initialWisdomModifierUnused = 1;
    initialCharismaUnused = 9;
    initialCharismaModifierUnused = 1;
    initialProficiencyBonus = 2;
    initialArmorClass = 18;
    initialInitiative = 2;
    initialSpeedUnused = 30;
    initialHitPoints = 12;
    initialRestDiceFaces = 10;
    weaponMasteryBonusHit.put("Battle Axe", 5);
    weaponMasteryBonusDamage.put("Battle Axe", 3);
    weaponMasteryBonusHit.put("Javelin", 5);
    weaponMasteryBonusDamage.put("Javelin", 3);
  }
}
